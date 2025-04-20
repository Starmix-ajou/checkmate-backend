package com.starmix.checkmate.adapter.out.ai.client.response;

import com.starmix.checkmate.domain.project.Suggestion;
import lombok.Builder;

@Builder
public record CreateFeatureDefinitionFeignResponse(
        Suggestion suggestion
) { }