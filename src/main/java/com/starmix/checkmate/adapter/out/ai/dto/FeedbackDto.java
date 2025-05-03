package com.starmix.checkmate.adapter.out.ai.dto;

import com.starmix.checkmate.adapter.out.ai.client.response.FeedbackFeatureDefinitionFeignResponse;
import com.starmix.checkmate.adapter.out.ai.client.response.FeedbackFeatureSpecificationFeignResponse;
import com.starmix.checkmate.domain.project.Feature;
import lombok.Builder;

import java.util.List;

@Builder
public record FeedbackDto(
        List<Feature> features,
        Boolean isNextStep
) {
    public static FeedbackDto fromFeatureDefinition(FeedbackFeatureDefinitionFeignResponse response) {
        List<Feature> features = response.features().stream()
                .map(Feature::fromFeatureName).toList();

        return FeedbackDto.builder()
                .features(features)
                .isNextStep(response.isNextStep())
                .build();
    }

    public static FeedbackDto fromFeatureSpecification(FeedbackFeatureSpecificationFeignResponse response) {
        return FeedbackDto.builder()
                .features(response.features())
                .isNextStep(response.isNextStep())
                .build();
    }
}
