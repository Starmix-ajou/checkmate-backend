package com.starmix.checkmate.adapter.out.ai.adapter;

import com.starmix.checkmate.adapter.out.ai.client.AIFeignClient;
import com.starmix.checkmate.adapter.out.ai.client.request.CreateFeatureDefinitionFeignRequest;
import com.starmix.checkmate.adapter.out.ai.client.request.CreateFeatureSpecificationFeignRequest;
import com.starmix.checkmate.adapter.out.ai.client.request.FeedbackFeignRequest;
import com.starmix.checkmate.adapter.out.ai.client.response.CreateFeatureDefinitionFeignResponse;
import com.starmix.checkmate.adapter.out.ai.client.response.CreateFeatureSpecificationFeignResponse;
import com.starmix.checkmate.adapter.out.ai.client.response.FeedbackFeatureDefinitionFeignResponse;
import com.starmix.checkmate.adapter.out.ai.client.response.FeedbackFeatureSpecificationFeignResponse;
import com.starmix.checkmate.adapter.out.ai.dto.FeedbackDto;
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
    public Suggestion createFunctionDefinition(Project project, String definitionUrl) {
        try {
            CreateFeatureDefinitionFeignRequest request = CreateFeatureDefinitionFeignRequest.builder()
                    .email(project.getLeader().getEmail())
                    .description(project.getDescription())
                    .definitionUrl(definitionUrl)
                    .build();
            CreateFeatureDefinitionFeignResponse response = aiFeignClient.createFeatureDefinition(request);
            return response.suggestion().toDomain();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomException("예상치 못한 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public FeedbackDto feedbackFeatureDefinition(String email, String feedback) {
        try {
            FeedbackFeignRequest request = FeedbackFeignRequest.builder()
                    .email(email)
                    .feedback(feedback)
                    .build();
            FeedbackFeatureDefinitionFeignResponse response = aiFeignClient.feedbackFeatureDefinition(request);
            return FeedbackDto.fromFeatureDefinition(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
            throw new CustomException("예상치 못한 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public FeedbackDto feedbackFeatureSpecification(String email, String feedback) {
        try {
            FeedbackFeignRequest request = FeedbackFeignRequest.builder()
                    .email(email)
                    .feedback(feedback)
                    .build();
            FeedbackFeatureSpecificationFeignResponse response = aiFeignClient.feedbackFeatureSpecification(request);
            return FeedbackDto.fromFeatureSpecification(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomException("예상치 못한 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
