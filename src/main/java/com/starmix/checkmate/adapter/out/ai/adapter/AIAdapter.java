package com.starmix.checkmate.adapter.out.ai.adapter;

import com.starmix.checkmate.adapter.out.ai.client.AIFeignClient;
import com.starmix.checkmate.adapter.out.ai.client.request.CreateFeatureDefinitionFeignRequest;
import com.starmix.checkmate.adapter.out.ai.client.request.CreateFeatureSpecificationFeignRequest;
import com.starmix.checkmate.adapter.out.ai.client.request.FeedbackFeignRequest;
import com.starmix.checkmate.adapter.out.ai.client.response.CreateFeatureDefinitionFeignResponse;
import com.starmix.checkmate.adapter.out.ai.client.response.CreateFeatureSpecificationFeignResponse;
import com.starmix.checkmate.adapter.out.ai.client.response.FeedbackFeignResponse;
import com.starmix.checkmate.application.port.out.ai.AIPort;
import com.starmix.checkmate.domain.project.Feature;
import com.starmix.checkmate.domain.project.Project;
import com.starmix.checkmate.domain.project.Suggestion;
import com.starmix.checkmate.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class AIAdapter implements AIPort {

    private final AIFeignClient aiFeignClient;

    @Override
    public Suggestion createFunctionDefinition(Project project) {
        try {
            CreateFeatureDefinitionFeignRequest request = CreateFeatureDefinitionFeignRequest.builder()
                    .description(project.getDescription())
                    .build();
            CreateFeatureDefinitionFeignResponse response = aiFeignClient.createFeatureDefinition(request);
            return response.suggestion();
        } catch (Exception e) {
            throw new CustomException("예상치 못한 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Feature> featureDefinitionFeedback(String email, String feedback) {
        try {
            FeedbackFeignRequest request = FeedbackFeignRequest.builder()
                    .email(email)
                    .feedback(feedback)
                    .build();
            FeedbackFeignResponse response = aiFeignClient.feedbackFeatureDefinition(request);
            return response.features();
        } catch (Exception e) {
            throw new CustomException("예상치 못한 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Feature> createFeatureSpecification(String email) {
        try {
            CreateFeatureSpecificationFeignRequest request = CreateFeatureSpecificationFeignRequest.builder()
                    .email(email)
                    .build();
            CreateFeatureSpecificationFeignResponse response = aiFeignClient.createFeatureSpecification(request);
            return response.features();
        } catch (Exception e) {
            throw new CustomException("예상치 못한 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Feature> featureSpecificationFeedback(String email, String feedback) {
        try {
            FeedbackFeignRequest request = FeedbackFeignRequest.builder()
                    .email(email)
                    .feedback(feedback)
                    .build();
            FeedbackFeignResponse response = aiFeignClient.feedbackFeatureSpecification(request);
            return response.features();
        } catch (Exception e) {
            throw new CustomException("예상치 못한 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
