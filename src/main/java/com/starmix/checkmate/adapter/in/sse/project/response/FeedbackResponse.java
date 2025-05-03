package com.starmix.checkmate.adapter.in.sse.project.response;

import com.starmix.checkmate.adapter.out.ai.dto.FeedbackDto;
import com.starmix.checkmate.domain.project.Feature;
import lombok.Builder;

import java.util.List;

@Builder
public record FeedbackResponse(
        List<Feature> features,
        Boolean isNextStep
) {
    public static FeedbackResponse fromFeedbackDto(FeedbackDto feedbackDto) {
        return FeedbackResponse.builder()
                .features(feedbackDto.features())
                .isNextStep(feedbackDto.isNextStep())
                .build();
    }
}
