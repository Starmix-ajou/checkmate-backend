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
        return TaskStatistics.builder()
                .todoCount(taskCount.todoCount())
                .inProgressCount(taskCount.inProgressCount())
                .doneCount(taskCount.doneCount())
                .totalCount(taskCount.totalCount())
                .doneRate((double) taskCount.doneCount() / taskCount.totalCount())
                .build();
    }
}
