package com.starmix.checkmate.adapter.out.persistence.mapper;

import com.starmix.checkmate.adapter.out.persistence.entity.TaskEntity;
import com.starmix.checkmate.domain.task.Priority;
import com.starmix.checkmate.domain.task.Task;

public class TaskMapper {

    public static Task toDomain(TaskEntity entity) {
        return Task.builder()
                .title(entity.getTitle())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .assignee(UserMapper.toDomain(entity.getAssignee()))
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .priority(Priority.getPriority(entity.getPriority()))
                .taskId(entity.getId())
                .epic(EpicMapper.toDomain(entity.getEpic()))
                .review(entity.getReview())
                .doneDate(entity.getDoneDate())
                .build();
    }

    public static TaskEntity toEntity(Task domain) {
        return TaskEntity.builder()
                .id(domain.getTaskId())
                .title(domain.getTitle())
                .description(domain.getDescription())
                .status(domain.getStatus())
                .assignee(UserMapper.toEntity(domain.getAssignee()))
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .priority(domain.getPriority().getPriorityNum())
                .epic(EpicMapper.toEntity(domain.getEpic()))
                .review(domain.getReview())
                .doneDate(domain.getDoneDate())
                .build();
    }
}
