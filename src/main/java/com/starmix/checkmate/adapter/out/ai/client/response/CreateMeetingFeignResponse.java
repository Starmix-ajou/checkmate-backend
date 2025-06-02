package com.starmix.checkmate.adapter.out.ai.client.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CreateMeetingFeignResponse(
        String summary,
        List<String> actionItems
) { }