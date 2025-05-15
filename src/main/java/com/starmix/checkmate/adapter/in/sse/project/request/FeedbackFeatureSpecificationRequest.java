package com.starmix.checkmate.adapter.in.sse.project.request;

import com.starmix.checkmate.domain.feature.Feature;
import lombok.Builder;

import java.util.List;

@Builder
public record FeedbackFeatureSpecificationRequest(
        String feedback,
        List<Feature> createdFeatures,
        List<Feature> modifiedFeatures,
        List<String> deletedFeatures
) { }
