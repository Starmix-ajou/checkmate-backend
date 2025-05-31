package com.starmix.checkmate.adapter.in.sse.web.project.response;

import com.starmix.checkmate.domain.project.Suggestion;
import lombok.Builder;

@Builder
public record CreateFeatureDefinitionResponse(
        Suggestion suggestion
) { }
