package com.starmix.checkmate.adapter.in.rest.common.response.statistics;

import com.starmix.checkmate.domain.project.Project;
import lombok.Builder;

@Builder
public record ProjectDailyScrumStatistics(
        Project project,
        DailyScrumStatistics statistics
) {
    public static ProjectDailyScrumStatistics from(Project project, DailyScrumStatistics statistics) {
        return ProjectDailyScrumStatistics.builder()
                .project(project)
                .statistics(statistics)
                .build();
    }
}