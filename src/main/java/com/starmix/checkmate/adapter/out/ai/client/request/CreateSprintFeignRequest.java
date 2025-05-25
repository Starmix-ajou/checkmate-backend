package com.starmix.checkmate.adapter.out.ai.client.request;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record CreateSprintFeignRequest(
        String projectId,
        List<String> pendingTaskIds,
        LocalDate startDate
) { }