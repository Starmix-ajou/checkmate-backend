package com.starmix.checkmate.adapter.in.rest.web.project.request;

import java.time.LocalDate;

public record UpdateProjectRequest(
        String title,
        String description,
        String imageUrl,
        LocalDate endDate
) {
}
