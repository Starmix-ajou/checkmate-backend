package com.starmix.checkmate.application.service;

import com.starmix.checkmate.adapter.in.http.project.request.ProjectStatus;
import com.starmix.checkmate.adapter.in.http.project.response.ProjectsResponse;
import com.starmix.checkmate.adapter.in.sse.project.request.CreateFeatureDefinitionRequest;
import com.starmix.checkmate.adapter.in.sse.project.request.FeedbackFeatureSpecificationRequest;
import com.starmix.checkmate.adapter.in.sse.project.request.FeedbackRequest;
import com.starmix.checkmate.adapter.in.sse.project.response.CreateFeatureDefinitionResponse;
import com.starmix.checkmate.adapter.in.sse.project.response.CreateFeatureSpecificationResponse;
import com.starmix.checkmate.adapter.in.sse.project.response.FeedbackResponse;
import com.starmix.checkmate.adapter.out.ai.dto.FeedbackDto;
import com.starmix.checkmate.adapter.out.mail.type.MailType;
import com.starmix.checkmate.adapter.out.redis.RedisType;
import com.starmix.checkmate.application.port.out.ai.AIPort;
import com.starmix.checkmate.application.port.out.mail.MailPort;
import com.starmix.checkmate.application.port.out.persistence.ProjectPersistencePort;
import com.starmix.checkmate.application.port.out.persistence.UserPersistencePort;
import com.starmix.checkmate.application.port.out.redis.RedisPort;
import com.starmix.checkmate.domain.feature.Feature;
import com.starmix.checkmate.domain.project.Project;
import com.starmix.checkmate.domain.project.Suggestion;
import com.starmix.checkmate.domain.user.Profile;
import com.starmix.checkmate.domain.user.User;
import com.starmix.checkmate.global.exception.CustomException;
import com.starmix.checkmate.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ProjectService {
    private final ProjectPersistencePort projectPersistencePort;
    private final AIPort aiPort;
    private final RedisPort redisPort;
    private final JwtUtil jwtUtil;
    private final UserPersistencePort userPersistencePort;
    private final MailPort mailPort;

    public List<ProjectsResponse> getProjects(ProjectStatus status) {
        String email = jwtUtil.extractEmail();
        User user = userPersistencePort.findByEmail(email)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.FORBIDDEN));

        return switch (status) {
            case ACTIVE -> {
                List<Project> projects = projectPersistencePort.findActiveProjects(user.getUserId());
                yield ProjectsResponse.toProjectResponse(user, projects);
            }
            case ARCHIVED -> {
                List<Project> projects = projectPersistencePort.findArchivedProjects(user.getUserId());
                yield ProjectsResponse.toProjectResponse(user, projects);
            }
            case PENDING -> {
                List<String> pendingProjectIds = user.getProfiles().stream()
                        .filter(profile -> !profile.getIsActive())
                        .map(Profile::getProjectId)
                        .toList();
                if(pendingProjectIds.isEmpty()) {
                    yield List.of();
                }

                List<Project> projects = pendingProjectIds.stream()
                        .map(projectId -> projectPersistencePort.findById(projectId).orElse(null))
                        .toList();
                yield ProjectsResponse.toProjectResponse(user, projects);
            }
            case null -> {
                List<Project> projects = projectPersistencePort.findByMemberIdsContaining(user.getUserId());
                yield ProjectsResponse.toProjectResponse(user, projects);
            }
        };
    }

    public Project getProject(String projectId) {
        return projectPersistencePort.findById(projectId)
                .orElseThrow(() -> new CustomException("Project not found", HttpStatus.NOT_FOUND));
    }

    public CreateFeatureDefinitionResponse createFeatureDefinition(CreateFeatureDefinitionRequest request) {
        String email = jwtUtil.extractEmail();
        User leader = userPersistencePort.findByEmail(email)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.FORBIDDEN));
        List<User> members = request.members().stream().map(
                member -> userPersistencePort.findByEmail(member.email())
                        .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND))
        ).toList();

        Project project = Project.createTemporaryProject(request, leader, members);
        redisPort.saveObject(RedisType.PROJECT_INFO, email, project);

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

            Map<String, Context> contexts = project.toMailContext();
            contexts.forEach((memberEmail, context) -> mailPort.send(memberEmail, MailType.PROJECT_INVITE, context));
        }

        return FeedbackResponse.fromFeedbackDto(response, project.getProjectId());
    }

    public void approve(String projectId) {
        String email = jwtUtil.extractEmail();

        Project project = projectPersistencePort.findById(projectId)
                .orElseThrow(() -> new CustomException("Project not found", HttpStatus.NOT_FOUND));
        User user = userPersistencePort.findByEmail(email)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.FORBIDDEN));

        user.approve(project.getProjectId());
        userPersistencePort.save(user);
    }

    public void deny(String projectId) {
        String email = jwtUtil.extractEmail();

        Project project = projectPersistencePort.findById(projectId)
                .orElseThrow(() -> new CustomException("Project not found", HttpStatus.NOT_FOUND));
        User user = userPersistencePort.findByEmail(email)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.FORBIDDEN));

        user.deny(project.getProjectId());
        userPersistencePort.save(user);
    }
}