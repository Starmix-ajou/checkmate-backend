package com.starmix.checkmate.adapter.out.ai.client.response;

import com.starmix.checkmate.domain.project.Feature;
import lombok.Builder;

import java.util.List;

@Builder
public record FeedbackFeatureSpecificationFeignResponse(
        List<Feature> features,
        Boolean isNextStep
) { }