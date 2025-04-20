package com.starmix.checkmate.adapter.in.sse.project.request;

import com.starmix.checkmate.domain.user.User;
import lombok.Builder;

@Builder
public record FeatureDefinitionFeedbackRequest(
        String description
) { }
