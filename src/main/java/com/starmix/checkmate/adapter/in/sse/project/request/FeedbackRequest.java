package com.starmix.checkmate.adapter.in.sse.project.request;

import lombok.Builder;

@Builder
public record FeedbackRequest(
        String feedback
) { }
