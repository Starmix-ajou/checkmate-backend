package com.starmix.checkmate.adapter.in.http.project.request;

import java.time.LocalDate;

public record UpdateProjectRequest(
        String title,
        String description,
        String imageUrl,
        LocalDate endDate
) {
}
