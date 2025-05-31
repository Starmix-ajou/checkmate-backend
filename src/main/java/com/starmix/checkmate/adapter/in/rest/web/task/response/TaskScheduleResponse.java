package com.starmix.checkmate.adapter.in.rest.web.task.response;

import com.starmix.checkmate.domain.task.Task;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record TaskScheduleResponse(
        LocalDate date,
        List<TaskScheduleBrief> tasks
) {
    public static TaskScheduleResponse fromDomain(List<Task> tasks, LocalDate date) {
        List<TaskScheduleBrief> taskScheduleBriefs = tasks.stream()
                .map(TaskScheduleBrief::fromDomain).toList();
        return TaskScheduleResponse.builder()
                .date(date)
                .tasks(taskScheduleBriefs)
                .build();
    }
}
