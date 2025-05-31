package com.starmix.checkmate.adapter.in.sse.web.project.request;

import lombok.Builder;

@Builder
public record FeedbackRequest(
        String feedback
) { }
