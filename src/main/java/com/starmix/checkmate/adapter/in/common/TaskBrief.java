package com.starmix.checkmate.adapter.in.common;

import com.starmix.checkmate.domain.task.Priority;

import java.time.LocalDate;

public record TaskBrief(
        String title,
        String description,
        String assigneeEmail,
        LocalDate startDate,
        LocalDate endDate,
        Priority priority
) { }
