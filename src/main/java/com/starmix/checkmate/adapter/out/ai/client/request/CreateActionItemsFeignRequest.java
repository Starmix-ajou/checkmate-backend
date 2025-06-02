package com.starmix.checkmate.adapter.out.ai.client.request;

import lombok.Builder;

import java.util.List;

@Builder
public record CreateActionItemsFeignRequest(
        String projectId,
        List<String> actionItems
) {
    public static CreateActionItemsFeignRequest from(String projectId, List<String> actionItems) {
        return CreateActionItemsFeignRequest.builder()
                .projectId(projectId)
                .actionItems(actionItems)
                .build();
    }
}