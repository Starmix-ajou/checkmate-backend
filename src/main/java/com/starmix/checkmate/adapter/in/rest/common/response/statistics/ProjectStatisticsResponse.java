package com.starmix.checkmate.adapter.in.rest.common.response.statistics;

import com.starmix.checkmate.adapter.out.persistence.dto.TaskCountPersistenceDto;
import lombok.Builder;

@Builder
public record ProjectStatisticsResponse(
        String projectId,
        TaskStatistics taskStatistics,
        DailyScrumStatistics dailyScrumStatistics,
        ReviewStatistics reviewStatistics
) {
    public static ProjectStatisticsResponse from(
            String projectId,
            TaskCountPersistenceDto taskCount,
            Integer doneDays,
            Integer totalDays,
            Integer doneCount
    ) {
        return ProjectStatisticsResponse.builder()
                .projectId(projectId)
                .taskStatistics(TaskStatistics.from(taskCount))
                .dailyScrumStatistics(DailyScrumStatistics.from(doneDays, totalDays))
                .reviewStatistics(doneCount == null ? null : ReviewStatistics.from(taskCount.totalCount(), doneCount))
                .build();
    }
}
