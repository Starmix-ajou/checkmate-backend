package com.starmix.checkmate.adapter.in.rest.common.response.statistics;

import com.starmix.checkmate.domain.project.Project;
import lombok.Builder;

@Builder
public record ProjectTaskStatistics(
        Project project,
        TaskStatistics statistics
) {
    public static ProjectTaskStatistics from(Project project, TaskStatistics statistics) {
        return ProjectTaskStatistics.builder()
                .project(project)
                .statistics(statistics)
                .build();
    }
}