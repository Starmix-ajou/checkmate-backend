package com.starmix.checkmate.adapter.out.persistence.mapper;

import com.starmix.checkmate.adapter.out.persistence.entity.TaskEntity;
import com.starmix.checkmate.domain.task.Task;

import java.time.LocalDateTime;

public class TaskMapper {

    public static Task toDomain(TaskEntity entity) {
        return Task.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .assignee(UserMapper.toDomain(entity.getAssignee()))
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .priority(entity.getPriority())
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static TaskEntity toEntity(Task domain) {
        return TaskEntity.builder()
                .name(domain.getName())
                .description(domain.getDescription())
                .status(domain.getStatus())
                .assignee(UserMapper.toEntity(domain.getAssignee()))
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .priority(domain.getPriority())
                .build();
    }
    public static TaskEntity updateEntity(TaskEntity entity, Task domain) {
        return TaskEntity.builder()
                .name(domain.getName())
                .description(domain.getDescription())
                .status(domain.getStatus())
                .assignee(UserMapper.toEntity(domain.getAssignee()))
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .priority(domain.getPriority())
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
