package com.starmix.checkmate.adapter.in.common;

import com.starmix.checkmate.domain.project.Project;
import lombok.Builder;

@Builder
public record ProjectBriefDto(
        String projectId,
        String title,
        String imageUrl
) {
    public static ProjectBriefDto fromDomain(Project project) {
        return ProjectBriefDto.builder()
                .projectId(project.getProjectId())
                .title(project.getTitle())
                .imageUrl(project.getImageUrl())
                .build();
    }
}
