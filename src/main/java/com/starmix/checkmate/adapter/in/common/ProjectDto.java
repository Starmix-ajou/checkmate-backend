package com.starmix.checkmate.adapter.in.common;

import com.starmix.checkmate.domain.project.Project;
import lombok.Builder;

@Builder
public record ProjectDto(
        String projectId,
        String title,
        String imageUrl
) {
    public static ProjectDto fromDomain(Project project) {
        return ProjectDto.builder()
                .projectId(project.getProjectId())
                .title(project.getTitle())
                .imageUrl(project.getImageUrl())
                .build();
    }
}
