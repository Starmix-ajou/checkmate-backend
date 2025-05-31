package com.starmix.checkmate.adapter.in.rest.common.response;

import com.starmix.checkmate.domain.project.Project;
import lombok.Builder;

@Builder
public record ProjectBriefResponse(
        String projectId,
        String title,
        String description
) {
    public static ProjectBriefResponse fromDomain(Project project) {
        return ProjectBriefResponse.builder()
                .projectId(project.getProjectId())
                .title(project.getTitle())
                .description(project.getDescription())
                .build();
    }
}
