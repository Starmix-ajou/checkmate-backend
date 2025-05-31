package com.starmix.checkmate.adapter.in.sse.web.project.response;

import com.starmix.checkmate.adapter.out.ai.dto.FeedbackDto;
import com.starmix.checkmate.domain.feature.Feature;
import lombok.Builder;

import java.util.List;

@Builder
public record FeedbackResponse(
        List<Feature> features,
        Boolean isNextStep,
        String projectId
) {
    public static FeedbackResponse fromFeedbackDto(FeedbackDto feedbackDto, String projectId) {
        return FeedbackResponse.builder()
                .features(feedbackDto.features())
                .isNextStep(feedbackDto.isNextStep())
                .projectId(projectId)
                .build();
    }

    public static FeedbackResponse fromFeedbackDto(FeedbackDto feedbackDto) {
        return FeedbackResponse.builder()
                .features(feedbackDto.features())
                .isNextStep(feedbackDto.isNextStep())
                .projectId(null)
                .build();
    }
}
