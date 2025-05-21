package com.starmix.checkmate.adapter.out.persistence.mapper;

import com.starmix.checkmate.adapter.out.persistence.entity.EpicEntity;
import com.starmix.checkmate.domain.epic.Epic;

public class EpicMapper {

    public static Epic toDomain(EpicEntity entity) {
        return Epic.builder()
                .epicId(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .projectId(entity.getProjectId())
                .featureId(entity.getFeatureId())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .build();
    }

    public static EpicEntity toEntity(Epic domain) {
        return EpicEntity.builder()
                .id(domain.getEpicId())
                .title(domain.getTitle())
                .description(domain.getDescription())
                .projectId(domain.getProjectId())
                .featureId(domain.getFeatureId())
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .build();
    }
}
