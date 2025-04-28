package com.starmix.checkmate.adapter.out.persistence.mapper;

import com.starmix.checkmate.adapter.out.persistence.entity.DailyScrumEntity;
import com.starmix.checkmate.adapter.out.persistence.entity.TaskEntity;
import com.starmix.checkmate.domain.dailyScrum.DailyScrum;
import com.starmix.checkmate.domain.task.Task;

import java.time.LocalDateTime;
import java.util.List;

public class DailyScrumMapper {

    public static DailyScrum toDomain(DailyScrumEntity entity) {
        List<Task> todoTasks = entity.getTodoTasks().stream()
                .map(TaskMapper::toDomain).toList();
        List<Task> doneTasks = entity.getDoneTasks().stream()
                .map(TaskMapper::toDomain).toList();

        return DailyScrum.builder()
                .timestamp(entity.getTimestamp())
                .todoTasks(todoTasks)
                .doneTasks(doneTasks)
                .projectId(entity.getProjectId())
                .dailyScrumId(entity.getId())
                .build();
    }

    public static DailyScrumEntity toEntity(DailyScrum domain) {
        List<TaskEntity> todoTasks = domain.getTodoTasks().stream()
                .map(TaskMapper::toEntity).toList();
        List<TaskEntity> doneTasks = domain.getDoneTasks().stream()
                .map(TaskMapper::toEntity).toList();

        return DailyScrumEntity.builder()
                .timestamp(domain.getTimestamp())
                .todoTasks(todoTasks)
                .doneTasks(doneTasks)
                .projectId(domain.getProjectId())
                .build();
    }
    public static DailyScrumEntity updateEntity(DailyScrumEntity entity, DailyScrum domain) {
        List<TaskEntity> todoTasks = domain.getTodoTasks().stream()
                .map(TaskMapper::toEntity).toList();
        List<TaskEntity> doneTasks = domain.getDoneTasks().stream()
                .map(TaskMapper::toEntity).toList();

        return DailyScrumEntity.builder()
                .timestamp(entity.getTimestamp())
                .todoTasks(todoTasks)
                .doneTasks(doneTasks)
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
