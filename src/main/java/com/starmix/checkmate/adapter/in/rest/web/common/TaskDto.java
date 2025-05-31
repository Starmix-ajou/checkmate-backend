package com.starmix.checkmate.adapter.in.rest.web.common;

import com.starmix.checkmate.domain.task.Priority;
import com.starmix.checkmate.domain.task.Status;
import com.starmix.checkmate.domain.task.Task;
import com.starmix.checkmate.domain.user.User;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TaskDto(
        String taskId,
        String title,
        String description,
        Status status,
        User assignee,
        LocalDate startDate,
        LocalDate endDate,
        Priority priority,
        EpicDto epic
) {
    public static TaskDto fromDomain(Task task, EpicDto epic) {
        return TaskDto.builder()
                .taskId(task.getTaskId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .assignee(task.getAssignee())
                .startDate(task.getStartDate())
                .endDate(task.getEndDate())
                .priority(task.getPriority())
                .epic(epic)
                .build();
    }
}
