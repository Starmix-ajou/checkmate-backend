package com.starmix.checkmate.adapter.out.ai.client.response;

import com.starmix.checkmate.domain.project.Feature;
import com.starmix.checkmate.domain.project.Suggestion;
import lombok.Builder;

import java.util.List;

@Builder
public record CreateFeatureSpecificationFeignResponse(
        List<Feature> features
) { }