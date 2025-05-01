package com.starmix.checkmate.adapter.in.http.task.request;

import com.starmix.checkmate.domain.task.Priority;
import com.starmix.checkmate.domain.task.Status;

import java.time.LocalDate;

public record UpdateTaskRequest(
        String title,
        String description,
        Status status,
        String assigneeEmail,
        LocalDate startDate,
        LocalDate endDate,
        Priority priority,
        String epicId
) { }