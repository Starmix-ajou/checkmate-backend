package com.starmix.checkmate.adapter.out.persistence.mapper;

import com.starmix.checkmate.adapter.out.persistence.entity.EpicEntity;
import com.starmix.checkmate.domain.epic.Epic;

import java.time.LocalDateTime;

public class EpicMapper {

    public static Epic toDomain(EpicEntity entity) {
        return Epic.builder()
                .title(entity.getTitle())
                .description(entity.getDescription())
                .projectId(entity.getProjectId())
                .epicId(entity.getId())
                .build();
    }

    public static EpicEntity toEntity(Epic domain) {
        return EpicEntity.builder()
                .id(domain.getEpicId())
                .title(domain.getTitle())
                .description(domain.getDescription())
                .projectId(domain.getProjectId())
                .build();
    }
    public static EpicEntity updateEntity(EpicEntity entity, Epic domain) {
        return EpicEntity.builder()
                .title(domain.getTitle())
                .description(domain.getDescription())
                .projectId(domain.getProjectId())
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
