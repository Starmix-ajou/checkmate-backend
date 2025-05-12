package com.starmix.checkmate.adapter.in.http.test.request;

import com.starmix.checkmate.domain.sprint.Sprint;

import java.time.LocalDate;

public record CreateSprintTestRequest(
        String title,
        String description,
        String projectId,
        LocalDate startDate,
        LocalDate endDate
) {
    public Sprint toDomain(Integer sequence) {
        return Sprint.create(
                this.title, this.description, sequence,
                this.projectId, this.startDate, this.endDate
        );
    }
}
