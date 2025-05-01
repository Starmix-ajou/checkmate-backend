package com.starmix.checkmate.application.service;

import com.starmix.checkmate.adapter.in.sse.project.request.CreateFeatureDefinitionRequest;
import com.starmix.checkmate.adapter.in.sse.project.request.FeedbackRequest;
import com.starmix.checkmate.adapter.in.sse.project.response.CreateFeatureDefinitionResponse;
import com.starmix.checkmate.adapter.in.sse.project.response.CreateFeatureSpecificationResponse;
import com.starmix.checkmate.adapter.in.sse.project.response.FeedbackResponse;
import com.starmix.checkmate.adapter.out.ai.client.response.FeedbackFeignResponse;
import com.starmix.checkmate.adapter.out.mail.type.MailType;
import com.starmix.checkmate.adapter.out.redis.RedisType;
import com.starmix.checkmate.application.port.out.mail.MailPort;
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
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ProjectTestService {
    private final ProjectPersistencePort projectPersistencePort;
    private final RedisPort redisPort;
    private final JwtUtil jwtUtil;
    private final UserPersistencePort userPersistencePort;
    private final MailPort mailPort;

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

        Suggestion suggestion = Suggestion.builder()
                .features(List.of(
                        Feature.builder()
                                .name("테스트 기능")
                                .build()
                ))
                .suggestions(List.of(new Suggestion.Topic("Q1", List.of("A1"))))
                .build();

//                aiPort.createFunctionDefinition(project, request.definitionUrl());

        return CreateFeatureDefinitionResponse.builder()
                .suggestion(suggestion)
                .build();
    }

    public FeedbackResponse feedbackFeatureDefinition(FeedbackRequest request) {
        String email = jwtUtil.extractEmail();

//        FeedbackFeignResponse response = aiPort.feedbackFeatureDefinition(email, request.feedback());
        FeedbackFeignResponse response = FeedbackFeignResponse.builder()
                .features((List.of(
                        Feature.builder()
                                .name("테스트 기능")
                                .build()
                )))
                .isNextStep(true)
                .build();

        return FeedbackResponse.builder()
                .features(response.features())
                .isNextStep(response.isNextStep())
                .build();
    }

    public CreateFeatureSpecificationResponse createFeatureSpecification() {
        String email = jwtUtil.extractEmail();

//        List<Feature> features = aiPort.createFeatureSpecification(email);
        List<Feature> features = List.of(
                Feature.builder()
                        .name("테스트 기능")
                        .useCase("테스트 목적")
                        .input("테스트 인풋")
                        .output("테스트 아웃풋")
                        .build()
        );

        return CreateFeatureSpecificationResponse.builder()
                .features(features)
                .build();
    }

    public FeedbackResponse feedbackFeatureSpecification(FeedbackRequest request) {
        String email = jwtUtil.extractEmail();

//        FeedbackFeignResponse response = aiPort.feedbackFeatureSpecification(email, request.feedback());
        FeedbackFeignResponse response = FeedbackFeignResponse.builder()
                .features((List.of(
                        Feature.builder()
                                .name("테스트 기능")
                                .build()
                )))
                .isNextStep(true)
                .build();

        if(response.isNextStep()) {
            Project project = redisPort.getObject(RedisType.PROJECT_INFO, email);

            User leader = userPersistencePort.findById(project.getLeader().getUserId())
                    .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
            List<User> members = project.getMembers().stream().map(
                    member -> userPersistencePort.findById(member.getUserId())
                            .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND))
            ).toList();
            project.createProject(leader, members);

            userPersistencePort.save(leader);
            members.forEach(userPersistencePort::save);
            projectPersistencePort.save(project);

            Map<String, Context> contexts = project.toMailContext();
            contexts.forEach((memberEmail, context) -> mailPort.send(memberEmail, MailType.PROJECT_INVITE, context));
        }

        return FeedbackResponse.builder()
                .features(response.features())
                .isNextStep(response.isNextStep())
                .build();
    }
}