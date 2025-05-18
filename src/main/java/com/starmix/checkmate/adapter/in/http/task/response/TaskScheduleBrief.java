package com.starmix.checkmate.adapter.in.http.task.response;

import com.starmix.checkmate.domain.task.Status;
import com.starmix.checkmate.domain.task.Task;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TaskScheduleBrief(
        String title,
        LocalDate endDate,
        Status status
) {
    public static TaskScheduleBrief fromDomain(Task task) {
        return TaskScheduleBrief.builder()
                .title(task.getTitle())
                .endDate(task.getEndDate())
                .status(task.getStatus())
                .build();
    }
}
