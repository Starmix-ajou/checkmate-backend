package com.starmix.checkmate.adapter.out.persistence.mapper;

import com.starmix.checkmate.adapter.out.persistence.entity.FeatureEntity;
import com.starmix.checkmate.domain.feature.Feature;
import com.starmix.checkmate.domain.task.Priority;

public class FeatureMapper {
    public static Feature toDomain(FeatureEntity entity) {
        return Feature.builder()
                .featureId(entity.getId())
                .name(entity.getName())
                .useCase(entity.getUseCase())
                .input(entity.getInput())
                .output(entity.getOutput())
                .preCondition(entity.getPreCondition())
                .postCondition(entity.getPostCondition())
                .stacks(entity.getStacks())
                .time(entity.getTime())
                .difficulty(entity.getDifficulty())
                .priority(Priority.getPriority(entity.getPriority()))
                .projectId(entity.getProjectId())
                .build();
    }

    public static FeatureEntity toEntity(Feature domain) {
        return FeatureEntity.builder()
                .featureId(domain.getFeatureId())
                .name(domain.getName())
                .useCase(domain.getUseCase())
                .input(domain.getInput())
                .output(domain.getOutput())
                .preCondition(domain.getPreCondition())
                .postCondition(domain.getPostCondition())
                .stacks(domain.getStacks())
                .time(domain.getTime())
                .difficulty(domain.getDifficulty())
                .priority(domain.getPriority().getPriorityNum())
                .projectId(domain.getProjectId())
                .build();
    }
}
