package com.starmix.checkmate.application.service;

import com.starmix.checkmate.adapter.in.sse.project.request.CreateFeatureDefinitionRequest;
import com.starmix.checkmate.adapter.in.sse.project.request.FeatureDefinitionFeedbackRequest;
import com.starmix.checkmate.adapter.in.sse.project.response.CreateFeatureDefinitionResponse;
import com.starmix.checkmate.adapter.in.sse.project.response.FeatureDefinitionFeedbackResponse;
import com.starmix.checkmate.adapter.out.cache.CacheType;
import com.starmix.checkmate.adapter.out.oauth.dto.OAuthUserInfo;
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
        OAuthUserInfo oAuthUserInfo = googleOAuthPort.getUserInfo(jwt);
        String email = oAuthUserInfo.getEmail();

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

        cachePort.saveObject(CacheType.PROJECT_INFO_WITHOUT_DEF, email, project);

        Suggestion suggestion = aiPort.createFunctionDefinition(project);

        return CreateFeatureDefinitionResponse.builder()
                .suggestion(suggestion)
                .build();
    }

    public FeatureDefinitionFeedbackResponse featureDefinitionFeedback(FeatureDefinitionFeedbackRequest request) {
        Jwt jwt = jwtUtil.getToken();
        String email = googleOAuthPort.getUserInfo(jwt).getEmail();

        Project project = cachePort.getObject(CacheType.PROJECT_INFO_WITHOUT_DEF, email);
        project.addFeedback(request.description());

        List<Feature> features = aiPort.featureDefinitionFeedback(project);

        return FeatureDefinitionFeedbackResponse.builder()
                .features(features)
                .build();
    }
}