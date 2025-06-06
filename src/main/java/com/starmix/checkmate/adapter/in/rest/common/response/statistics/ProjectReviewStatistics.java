package com.starmix.checkmate.adapter.in.rest.common.response.statistics;

import com.starmix.checkmate.domain.project.Project;
import lombok.Builder;

@Builder
public record ProjectReviewStatistics(
        Project project,
        ReviewStatistics statistics
) {
    public static ProjectReviewStatistics from(Project project, ReviewStatistics statistics) {
        return ProjectReviewStatistics.builder()
                .project(project)
                .statistics(statistics)
                .build();
    }
}