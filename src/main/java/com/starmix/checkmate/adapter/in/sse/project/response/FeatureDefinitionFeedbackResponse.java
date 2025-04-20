package com.starmix.checkmate.adapter.in.sse.project.response;

import com.starmix.checkmate.domain.project.Feature;
import lombok.Builder;

import java.util.List;

@Builder
public record FeatureDefinitionFeedbackResponse(
        List<Feature> features
) { }
