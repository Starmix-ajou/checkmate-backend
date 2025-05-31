package com.starmix.checkmate.adapter.in.sse.web.sprint.request;

import com.starmix.checkmate.domain.task.Priority;
import com.starmix.checkmate.domain.task.Status;

import java.time.LocalDate;
import java.util.List;

public record UpdateSprintRequest(
        String epicId,
        List<CreateTaskBrief> tasks
) {
    public record CreateTaskBrief(
            String title,
            String description,
            Status status,
            String assigneeEmail,
            LocalDate startDate,
            LocalDate endDate,
            Priority priority
    ) { }
}