package com.starmix.checkmate.adapter.in.rest.common.response.statistics;

import com.starmix.checkmate.adapter.out.persistence.dto.TaskCountPersistenceDto;
import lombok.Builder;

@Builder
public record TaskStatistics(
        Integer todoCount,
        Integer inProgressCount,
        Integer doneCount,
        Integer totalCount,
        double doneRate
) {
    public static TaskStatistics from(TaskCountPersistenceDto taskCount) {
        int total = taskCount.totalCount();
        double doneRate = (total != 0)
                ? (double) taskCount.doneCount() / total
                : 0.0;
        return TaskStatistics.builder()
                .todoCount(taskCount.todoCount())
                .inProgressCount(taskCount.inProgressCount())
                .doneCount(taskCount.doneCount())
                .totalCount(total)
                .doneRate(doneRate)
                .build();
    }
}
