package com.starmix.checkmate.adapter.out.ai.client.response;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record CreateSprintFeignResponse(
        SprintBrief sprint,
        List<EpicWithFeatures> epics
) {
    public record EpicWithFeatures(
            String title,
            String description,
            List<String> featureIds
    ) { }

    public record SprintBrief(
            String title,
            String description,
            LocalDate startDate,
            LocalDate endDate
    ) { }
}