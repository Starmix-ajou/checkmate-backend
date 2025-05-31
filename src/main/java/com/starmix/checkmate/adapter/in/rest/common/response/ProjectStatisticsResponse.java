package com.starmix.checkmate.adapter.in.rest.common.response;

import com.starmix.checkmate.application.port.out.persistence.dto.TaskCountPersistenceDto;
import lombok.Builder;

@Builder
public record ProjectStatisticsResponse(
        TaskStatistics taskStatistics,
        DailyScrumStatistics dailyScrumStatistics,
        ReviewStatistics reviewStatistics
) {
    @Builder
    public record TaskStatistics(
            Integer todoCount,
            Integer inProgressCount,
            Integer doneCount,
            Integer totalCount,
            double doneRate
    ) {
        public static TaskStatistics from(TaskCountPersistenceDto taskCount) {
            return TaskStatistics.builder()
                    .todoCount(taskCount.todoCount())
                    .inProgressCount(taskCount.inProgressCount())
                    .doneCount(taskCount.doneCount())
                    .totalCount(taskCount.totalCount())
                    .doneRate((double) taskCount.doneCount() / taskCount.totalCount())
                    .build();
        }
    }

    @Builder
    public record DailyScrumStatistics(
            Integer totalDays,
            Integer doneDays,
            double doneRate
    ) {
        public static DailyScrumStatistics from(Integer doneDays, Integer totalDays) {
            return DailyScrumStatistics.builder()
                    .doneDays(doneDays)
                    .totalDays(totalDays)
                    .doneRate((double) doneDays/totalDays)
                    .build();
        }
    }

    @Builder
    public record ReviewStatistics(
            Integer totalCount,
            Integer doneCount,
            double doneRate
    ) {
        public static ReviewStatistics from(Integer totalCount, Integer doneCount) {
            return ReviewStatistics.builder()
                    .totalCount(totalCount)
                    .doneCount(doneCount)
                    .doneRate((double) doneCount/totalCount)
                    .build();
        }
    }

    public static ProjectStatisticsResponse from(
            TaskCountPersistenceDto taskCount,
            Integer doneDays,
            Integer totalDays,
            Integer doneCount
    ) {
        return ProjectStatisticsResponse.builder()
                .taskStatistics(TaskStatistics.from(taskCount))
                .dailyScrumStatistics(DailyScrumStatistics.from(doneDays, totalDays))
                .reviewStatistics(doneCount == null ? null : ReviewStatistics.from(taskCount.totalCount(), doneCount))
                .build();
    }
}
