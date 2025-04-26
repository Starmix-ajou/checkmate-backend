package com.starmix.checkmate.application.service;

import com.starmix.checkmate.adapter.in.http.project.request.ApproveRequest;
import com.starmix.checkmate.adapter.in.http.project.request.ProjectStatus;
import com.starmix.checkmate.adapter.in.sse.project.request.CreateFeatureDefinitionRequest;
import com.starmix.checkmate.adapter.in.sse.project.request.FeedbackRequest;
import com.starmix.checkmate.adapter.in.sse.project.response.CreateFeatureDefinitionResponse;
import com.starmix.checkmate.adapter.in.sse.project.response.CreateFeatureSpecificationResponse;
import com.starmix.checkmate.adapter.in.sse.project.response.FeedbackResponse;
import com.starmix.checkmate.adapter.out.ai.client.response.FeedbackFeignResponse;
import com.starmix.checkmate.adapter.out.mail.type.MailType;
import com.starmix.checkmate.adapter.out.redis.RedisType;
import com.starmix.checkmate.application.port.out.ai.AIPort;
import com.starmix.checkmate.application.port.out.mail.MailPort;
import com.starmix.checkmate.application.port.out.oauth.GoogleOAuthPort;
import com.starmix.checkmate.application.port.out.persistence.ProjectPersistencePort;
import com.starmix.checkmate.application.port.out.persistence.UserPersistencePort;
import com.starmix.checkmate.application.port.out.redis.RedisPort;
import com.starmix.checkmate.domain.project.Feature;
import com.starmix.checkmate.domain.project.Project;
import com.starmix.checkmate.domain.project.Suggestion;
import com.starmix.checkmate.domain.user.User;
import com.starmix.checkmate.global.exception.CustomException;
import com.starmix.checkmate.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ProjectService {
    private final ProjectPersistencePort projectPersistencePort;
    private final GoogleOAuthPort googleOAuthPort;
    private final AIPort aiPort;
    private final RedisPort redisPort;
    private final JwtUtil jwtUtil;
    private final UserPersistencePort userPersistencePort;
    private final MailPort mailPort;

    public List<Project> getProjects(ProjectStatus status) {
        Jwt jwt = jwtUtil.getToken();
        String email = googleOAuthPort.getUserInfo(jwt).getEmail();

        return switch (status) {
            case ACTIVE -> projectPersistencePort.findActiveProjects();
            case ARCHIVED -> projectPersistencePort.findArchivedProjects();
            case PENDING -> {
                User user = userPersistencePort.findByEmail(email)
                        .orElseThrow(() -> new CustomException("User not found", HttpStatus.FORBIDDEN));
                yield user.getPendingProjectIds().stream()
                        .map(projectId -> projectPersistencePort.findById(projectId).orElse(null))
                        .toList();
            }
            case null -> projectPersistencePort.findByMembersEmail(email);
        };
    }

    public CreateFeatureDefinitionResponse createFeatureDefinition(CreateFeatureDefinitionRequest request) {
        Jwt jwt = jwtUtil.getToken();
        String email = googleOAuthPort.getUserInfo(jwt).getEmail();

        Project project = Project.builder()
                .title(request.title())
                .description(request.description())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .members(request.members())
                .build();

        redisPort.saveObject(RedisType.PROJECT_INFO, email, project);

        Suggestion suggestion = aiPort.createFunctionDefinition(project, request.definitionUrl());

        redisPort.saveSet(RedisType.FEATURES, email, suggestion.getFeatures());

        return CreateFeatureDefinitionResponse.builder()
                .suggestion(suggestion)
                .build();
    }

    public FeedbackResponse feedbackFeatureDefinition(FeedbackRequest request) {
        Jwt jwt = jwtUtil.getToken();
        String email = googleOAuthPort.getUserInfo(jwt).getEmail();

        FeedbackFeignResponse response = aiPort.feedbackFeatureDefinition(email, request.feedback());
        redisPort.updateSet(RedisType.FEATURES, email, response.features());

        return FeedbackResponse.builder()
                .features(response.features())
                .isNextStep(response.isNextStep())
                .build();
    }

    public CreateFeatureSpecificationResponse createFeatureSpecification() {
        Jwt jwt = jwtUtil.getToken();
        String email = googleOAuthPort.getUserInfo(jwt).getEmail();

        List<Feature> features = aiPort.createFeatureSpecification(email);
        redisPort.updateSet(RedisType.FEATURES, email, features);

        return CreateFeatureSpecificationResponse.builder()
                .features(features)
                .build();
    }

    public FeedbackResponse feedbackFeatureSpecification(FeedbackRequest request) {
        Jwt jwt = jwtUtil.getToken();
        String email = googleOAuthPort.getUserInfo(jwt).getEmail();

        FeedbackFeignResponse response = aiPort.feedbackFeatureSpecification(email, request.feedback());
        if(response.isNextStep()) {
            redisPort.updateSet(RedisType.FEATURES, email, response.features());
        } else {
            Project project = redisPort.getObject(RedisType.PROJECT_INFO, email);
            project.getMembers().forEach(
                    member -> member.addPendingProject(project.getId())
            );
            projectPersistencePort.save(project);
            Map<String, Context> contexts = project.toMailContext();
            contexts.forEach((memberEmail, context) -> mailPort.send(memberEmail, MailType.PROJECT_INVITE, context));
        }

        return FeedbackResponse.builder()
                .features(response.features())
                .isNextStep(response.isNextStep())
                .build();
    }

    public void approve(String projectId, ApproveRequest request) {
        Jwt jwt = jwtUtil.getToken();
        String email = googleOAuthPort.getUserInfo(jwt).getEmail();

        User user = userPersistencePort.findByEmail(email)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.FORBIDDEN));
        if(!user.getPendingProjectIds().contains(projectId)) {
            throw new CustomException("Permission denied", HttpStatus.FORBIDDEN);
        }

        Project project = projectPersistencePort.findById(projectId)
                .orElseThrow(() -> new CustomException("Project not found", HttpStatus.NOT_FOUND));
        project.approve(user);
    }

    public void deny(String projectId) {
        Jwt jwt = jwtUtil.getToken();
        String email = googleOAuthPort.getUserInfo(jwt).getEmail();

        User user = userPersistencePort.findByEmail(email)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.FORBIDDEN));
        if(!user.getPendingProjectIds().contains(projectId)) {
            throw new CustomException("Permission denied", HttpStatus.FORBIDDEN);
        }
        user.denyPendingProject(projectId);
    }
}