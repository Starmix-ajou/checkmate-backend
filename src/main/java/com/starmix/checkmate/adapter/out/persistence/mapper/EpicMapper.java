package com.starmix.checkmate.adapter.out.persistence.mapper;

import com.starmix.checkmate.adapter.out.persistence.entity.EpicEntity;
import com.starmix.checkmate.domain.Epic;

import java.time.LocalDateTime;

public class EpicMapper {

    public static Epic toDomain(EpicEntity entity) {
        return Epic.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .projectId(entity.getProjectId())
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static EpicEntity toEntity(Epic domain) {
        return EpicEntity.builder()
                .name(domain.getName())
                .description(domain.getDescription())
                .projectId(domain.getProjectId())
                .build();
    }
    public static EpicEntity updateEntity(EpicEntity entity, Epic domain) {
        return EpicEntity.builder()
                .name(domain.getName())
                .description(domain.getDescription())
                .projectId(domain.getProjectId())
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
