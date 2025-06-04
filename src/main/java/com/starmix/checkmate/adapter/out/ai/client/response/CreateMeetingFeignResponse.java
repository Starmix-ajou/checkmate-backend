package com.starmix.checkmate.adapter.out.ai.client.response;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record CreateMeetingFeignResponse(
        String summary,
        List<ActionItem> actionItems
) {
    public record ActionItem(
            String title,
            String description,
            String assigneeId,
            LocalDate endDate,
            String epicId
    ) {}
}