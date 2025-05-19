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
                .expectedDays(entity.getExpectedDays())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
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
                .expectedDays(domain.getExpectedDays())
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .difficulty(domain.getDifficulty())
                .priority(domain.getPriority().getPriorityNum())
                .projectId(domain.getProjectId())
                .build();
    }
}
