package com.starmix.checkmate.adapter.out.ai.client.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CreateSprintFeignRequest(
        String projectId,
        List<String> pendingTaskIds
) { }