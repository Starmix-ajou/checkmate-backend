package com.starmix.checkmate.adapter.in.sse.project.response;

import com.starmix.checkmate.domain.feature.Feature;
import lombok.Builder;

import java.util.List;

@Builder
public record CreateFeatureSpecificationResponse(
        List<Feature> features
) { }
