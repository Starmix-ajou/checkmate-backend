package com.starmix.checkmate.application.service;

import com.starmix.checkmate.adapter.in.rest.web.project.request.InviteProjectRequest;
import com.starmix.checkmate.adapter.in.rest.web.project.request.ProjectStatus;
import com.starmix.checkmate.adapter.in.rest.web.project.request.UpdateMemberRequest;
import com.starmix.checkmate.adapter.in.rest.web.project.request.UpdateProjectRequest;
import com.starmix.checkmate.adapter.in.rest.web.project.response.ProjectsResponse;
import com.starmix.checkmate.adapter.in.sse.web.project.request.CreateFeatureDefinitionRequest;
import com.starmix.checkmate.adapter.in.sse.web.project.request.FeedbackFeatureSpecificationRequest;
import com.starmix.checkmate.adapter.in.sse.web.project.request.FeedbackRequest;
import com.starmix.checkmate.adapter.in.sse.web.project.response.CreateFeatureDefinitionResponse;
import com.starmix.checkmate.adapter.in.sse.web.project.response.CreateFeatureSpecificationResponse;
import com.starmix.checkmate.adapter.in.sse.web.project.response.FeedbackResponse;
import com.starmix.checkmate.adapter.out.ai.dto.FeedbackDto;
import com.starmix.checkmate.adapter.out.mail.type.MailType;
import com.starmix.checkmate.adapter.out.redis.RedisType;
import com.starmix.checkmate.application.port.out.ai.AIPort;
import com.starmix.checkmate.application.port.out.mail.MailPort;
import com.starmix.checkmate.application.port.out.persistence.EpicPersistencePort;
import com.starmix.checkmate.application.port.out.persistence.ProjectPersistencePort;
import com.starmix.checkmate.application.port.out.persistence.UserPersistencePort;
import com.starmix.checkmate.application.port.out.redis.RedisPort;
import com.starmix.checkmate.domain.epic.Epic;
import com.starmix.checkmate.domain.feature.Feature;
import com.starmix.checkmate.domain.project.Project;
import com.starmix.checkmate.domain.project.Suggestion;
import com.starmix.checkmate.domain.user.Profile;
import com.starmix.checkmate.domain.user.Role;
import com.starmix.checkmate.domain.user.User;
import com.starmix.checkmate.global.exception.CustomException;
import com.starmix.checkmate.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ProjectService {
    private final ProjectPersistencePort projectPersistencePort;
    private final AIPort aiPort;
    private final RedisPort redisPort;
    private final JwtUtil jwtUtil;
    private final UserPersistencePort userPersistencePort;
    private final MailPort mailPort;
    private final EpicPersistencePort epicPersistencePort;

    public List<ProjectsResponse> getProjects(ProjectStatus status) {
        User user = jwtUtil.extractUser();
        List<Profile> profiles = Optional.ofNullable(user.getProfiles()).orElse(Collections.emptyList());

        List<String> projectIds = switch (status) {
            case ACTIVE -> Profile.filterProjectIds(profiles, Profile::getIsActive);
            case PENDING -> Profile.filterProjectIds(profiles, p -> !p.getIsActive());
            case ARCHIVED -> Profile.filterProjectIds(profiles, p -> true);
            case null -> Profile.filterProjectIds(profiles, p -> true);
        };

        List<Project> projects = projectPersistencePort.findByProjectIds(projectIds);

        if (status == ProjectStatus.ARCHIVED) {
            projects = projects.stream()
                    .filter(Project::isArchived)
                    .toList();
        }

        return ProjectsResponse.toProjectResponse(user, projects);
    }

    public Project getProject(String projectId) {
        return projectPersistencePort.findById(projectId)
                .orElseThrow(() -> new CustomException("Project not found", HttpStatus.NOT_FOUND));
    }

    public CreateFeatureDefinitionResponse createFeatureDefinition(CreateFeatureDefinitionRequest request) {
        User leader = jwtUtil.extractUser();
        List<User> members = request.members().stream().map(
                member -> userPersistencePort.findByEmail(member.email())
                        .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND))
        ).toList();

        Project project = Project.createTemporaryProject(request, leader, members);
        redisPort.saveObject(RedisType.PROJECT_INFO, leader.getEmail(), project);

        Suggestion suggestion = aiPort.createFunctionDefinition(project, request.definitionUrl());

        return CreateFeatureDefinitionResponse.builder()
                .suggestion(suggestion)
                .build();
    }

    public FeedbackResponse feedbackFeatureDefinition(FeedbackRequest request) {
        String email = jwtUtil.extractEmail();
        FeedbackDto response = aiPort.feedbackFeatureDefinition(email, request.feedback());
        return FeedbackResponse.fromFeedbackDto(response);
    }

    public CreateFeatureSpecificationResponse createFeatureSpecification() {
        String email = jwtUtil.extractEmail();
        List<Feature> features = aiPort.createFeatureSpecification(email);
        return CreateFeatureSpecificationResponse.builder()
                .features(features)
                .build();
    }

    public FeedbackResponse feedbackFeatureSpecification(FeedbackFeatureSpecificationRequest request) {
        String email = jwtUtil.extractEmail();

        Project project = redisPort.getObject(RedisType.PROJECT_INFO, email);
        FeedbackDto response = aiPort.feedbackFeatureSpecification(email, request);
        if(response.isNextStep()) {
            List<User> members = project.getMembers().stream().map(
                    member -> {
                        User user = userPersistencePort.findById(member.getUserId())
                                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
                        user.addProfile(member.getProfileByProjectId(project.getProjectId()));
                        if(member.getUserId().equals(project.getLeader().getUserId())) {
                            user.approve(project.getProjectId());
                        }
                        return user;
                    }
            ).toList();

            members.forEach(userPersistencePort::save);
            projectPersistencePort.save(project);

            response.features().forEach(feature -> {
                Epic epic = Epic.fromFeature(feature, project.getProjectId());
                epicPersistencePort.save(epic);
            });

            Map<String, Context> contexts = project.toMailContext();
            contexts.forEach((memberEmail, context) -> mailPort.send(memberEmail, MailType.PROJECT_INVITE, context));
        }

        return FeedbackResponse.fromFeedbackDto(response, project.getProjectId());
    }

    public void approve(String projectId) {
        Project project = projectPersistencePort.findById(projectId)
                .orElseThrow(() -> new CustomException("Project not found", HttpStatus.NOT_FOUND));
        User user = jwtUtil.extractUser();

        user.approve(project.getProjectId());
        userPersistencePort.save(user);
    }

    public void deny(String projectId) {
        Project project = projectPersistencePort.findById(projectId)
                .orElseThrow(() -> new CustomException("Project not found", HttpStatus.NOT_FOUND));
        User user = jwtUtil.extractUser();

        user.deny(project.getProjectId());
        userPersistencePort.save(user);
    }

    public void deleteProject(String projectId) {
        isAuthorizedLeader(projectId);
        projectPersistencePort.delete(projectId);
    }

    public void updateProject(String projectId, UpdateProjectRequest request) {
        Project project = isAuthorizedLeader(projectId);
        project.update(
                request.title(), request.description(),
                request.endDate(), request.imageUrl()
        );
        projectPersistencePort.save(project);
    }

    public void invite(String projectId, InviteProjectRequest request) {
        Project project = isAuthorizedLeader(projectId);

        User user = userPersistencePort.findByEmail(request.email())
                        .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        if(request.role().equals(Role.DEVELOPER)) {
            user.addProfile(Profile.init(request.profile(), projectId));
            project.addMember(user);
        } else {
            user.addProfile(Profile.initProductManager(projectId));
            project.changeProductManager(user);
        }

        userPersistencePort.save(user);
        projectPersistencePort.save(project);

        Map<String, Context> contexts = project.toMailContext(user);
        contexts.forEach((memberEmail, context) -> mailPort.send(memberEmail, MailType.PROJECT_INVITE, context));
    }

    public void updateMember(String projectId, String memberId, UpdateMemberRequest request) {
        User member = isAuthorizedMember(projectId, memberId);
        member.getProfileByProjectId(projectId).updatePositions(request.positions());
        userPersistencePort.save(member);
    }

    public void deleteMember(String projectId, String memberId) {
        User member = isAuthorizedMember(projectId, memberId);
        member.deleteProfileByProjectId(projectId);
        userPersistencePort.save(member);

        Project project = projectPersistencePort.findById(projectId)
                .orElseThrow(() -> new CustomException("Project not found", HttpStatus.NOT_FOUND));
        project.deleteMember(member);
        projectPersistencePort.save(project);
    }

    private User isAuthorizedMember(String projectId, String memberId) {
        User user = jwtUtil.extractUser();
        Project project = projectPersistencePort.findById(projectId)
                .orElseThrow(() -> new CustomException("Project not found", HttpStatus.NOT_FOUND));
        User member = userPersistencePort.findById(memberId)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        if(!project.canManageMember(user, member)) {
            throw new CustomException("Permission Denied", HttpStatus.FORBIDDEN);
        }
        return member;
    }

    private Project isAuthorizedLeader(String projectId) {
        User leader = jwtUtil.extractUser();
        Project project = projectPersistencePort.findById(projectId)
                .orElseThrow(() -> new CustomException("Project not found", HttpStatus.NOT_FOUND));
        if(!project.isLeader(leader)) {
            throw new CustomException("Permission Denied", HttpStatus.FORBIDDEN);
        }
        return project;
    }

}
