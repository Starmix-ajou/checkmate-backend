package com.starmix.checkmate.application.service;

import com.starmix.checkmate.adapter.in.sse.project.request.CreateFeatureDefinitionRequest;
import com.starmix.checkmate.adapter.in.sse.project.request.FeedbackRequest;
import com.starmix.checkmate.adapter.in.sse.project.response.CreateFeatureDefinitionResponse;
import com.starmix.checkmate.adapter.in.sse.project.response.CreateFeatureSpecificationResponse;
import com.starmix.checkmate.adapter.in.sse.project.response.FeedbackResponse;
import com.starmix.checkmate.adapter.out.cache.CacheType;
import com.starmix.checkmate.application.port.out.ai.AIPort;
import com.starmix.checkmate.application.port.out.cache.CachePort;
import com.starmix.checkmate.application.port.out.oauth.GoogleOAuthPort;
import com.starmix.checkmate.application.port.out.persistence.ProjectPersistencePort;
import com.starmix.checkmate.domain.project.Feature;
import com.starmix.checkmate.domain.project.Project;
import com.starmix.checkmate.domain.project.Suggestion;
import com.starmix.checkmate.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProjectService {
    private final ProjectPersistencePort projectPersistencePort;
    private final GoogleOAuthPort googleOAuthPort;
    private final AIPort aiPort;
    private final CachePort cachePort;
    private final JwtUtil jwtUtil;

    public List<Project> getProjects() {
        Jwt jwt = jwtUtil.getToken();
        String email = googleOAuthPort.getUserInfo(jwt).getEmail();

        return projectPersistencePort.findByMembersEmail(email);
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

        cachePort.saveObject(CacheType.PROJECT_INFO, email, project);

        Suggestion suggestion = aiPort.createFunctionDefinition(project);

        cachePort.saveSet(CacheType.FEATURES, email, suggestion.getFeatures());

        return CreateFeatureDefinitionResponse.builder()
                .suggestion(suggestion)
                .build();
    }

    public FeedbackResponse feedbackFeatureDefinition(FeedbackRequest request) {
        Jwt jwt = jwtUtil.getToken();
        String email = googleOAuthPort.getUserInfo(jwt).getEmail();

        List<Feature> features = aiPort.featureDefinitionFeedback(email, request.feedback());
        cachePort.updateSet(CacheType.FEATURES, email, features);

        return FeedbackResponse.builder()
                .features(features)
                .build();
    }

    public CreateFeatureSpecificationResponse createFeatureSpecification() {
        Jwt jwt = jwtUtil.getToken();
        String email = googleOAuthPort.getUserInfo(jwt).getEmail();

        List<Feature> features = aiPort.createFeatureSpecification(email);
        cachePort.updateSet(CacheType.FEATURES, email, features);

        return CreateFeatureSpecificationResponse.builder()
                .features(features)
                .build();
    }

    public FeedbackResponse feedbackFeatureSpecification(FeedbackRequest request) {
        Jwt jwt = jwtUtil.getToken();
        String email = googleOAuthPort.getUserInfo(jwt).getEmail();

        if(request.feedback().isEmpty()) {
            Project project = cachePort.getObject(CacheType.PROJECT_INFO, email);
            projectPersistencePort.save(project);
        }

        List<Feature> features = aiPort.featureDefinitionFeedback(email, request.feedback());
        cachePort.updateSet(CacheType.FEATURES, email, features);

        return FeedbackResponse.builder()
                .features(features)
                .build();
    }
}