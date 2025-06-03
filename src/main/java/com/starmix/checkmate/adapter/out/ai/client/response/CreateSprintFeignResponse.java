package com.starmix.checkmate.adapter.out.ai.client.response;

import com.starmix.checkmate.domain.task.Priority;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record CreateSprintFeignResponse(
        SprintBrief sprint,
        List<EpicWithFeatures> epics
) {
    public record EpicWithFeatures(
            String epicId,
            List<TaskBrief> tasks
    ) {
        public record TaskBrief(
                String title,
                String description,
                String assigneeId,
                LocalDate startDate,
                LocalDate endDate,
                Priority priority
        ) { }
    }

    public record SprintBrief(
            String title,
            String description,
            LocalDate startDate,
            LocalDate endDate
    ) { }
}