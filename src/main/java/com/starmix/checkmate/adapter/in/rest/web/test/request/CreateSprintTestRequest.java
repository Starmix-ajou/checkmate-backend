package com.starmix.checkmate.adapter.in.rest.web.test.request;

import java.time.LocalDate;

public record CreateSprintTestRequest(
        String title,
        String description,
        String projectId,
        LocalDate startDate,
        LocalDate endDate
) { }
