package com.starmix.checkmate.adapter.out.persistence.mapper;

import com.starmix.checkmate.adapter.out.persistence.entity.SprintEntity;
import com.starmix.checkmate.domain.sprint.Sprint;

import java.time.LocalDateTime;

public class SprintMapper {

    public static Sprint toDomain(SprintEntity entity) {
        return Sprint.builder()
                .title(entity.getTitle())
                .description(entity.getDescription())
                .sequence(entity.getSequence())
                .projectId(entity.getProjectId())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .sprintId(entity.getId())
                .build();
    }

    public static SprintEntity toEntity(Sprint domain) {
        return SprintEntity.builder()
                .title(domain.getTitle())
                .description(domain.getDescription())
                .sequence(domain.getSequence())
                .projectId(domain.getProjectId())
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .build();
    }
}
