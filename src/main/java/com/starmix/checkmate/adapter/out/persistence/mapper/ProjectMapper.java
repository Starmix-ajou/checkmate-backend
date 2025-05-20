package com.starmix.checkmate.adapter.out.persistence.mapper;

import com.starmix.checkmate.adapter.out.persistence.entity.ProjectEntity;
import com.starmix.checkmate.domain.project.Project;

public class ProjectMapper {

    public static Project toDomain(ProjectEntity entity) {
        return Project.builder()
                .title(entity.getTitle())
                .description(entity.getDescription())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .members(entity.getMembers())
                .leader(entity.getLeader())
                .imageUrl(entity.getImageUrl())
                .projectId(entity.getId())
                .build();
    }

    public static ProjectEntity toEntity(Project domain) {
        return ProjectEntity.builder()
                .id(domain.getProjectId())
                .title(domain.getTitle())
                .description(domain.getDescription())
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .members(domain.getMembers())
                .leader(domain.getLeader())
                .imageUrl(domain.getImageUrl())
                .build();
    }

}
