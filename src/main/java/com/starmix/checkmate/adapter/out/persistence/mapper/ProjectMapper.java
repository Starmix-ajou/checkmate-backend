package com.starmix.checkmate.adapter.out.persistence.mapper;

import com.starmix.checkmate.adapter.out.persistence.entity.ProjectEntity;
import com.starmix.checkmate.adapter.out.persistence.entity.UserEntity;
import com.starmix.checkmate.domain.project.Project;
import com.starmix.checkmate.domain.user.User;

import java.time.LocalDateTime;
import java.util.List;

public class ProjectMapper {

    public static Project toDomain(ProjectEntity entity) {
        List<User> members = entity.getMembers().stream()
                .map(UserMapper::toDomain).toList();

        return Project.builder()
                .title(entity.getTitle())
                .description(entity.getDescription())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .stacks(entity.getStacks())
                .members(members)
                .leader(UserMapper.toDomain(entity.getLeader()))
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static ProjectEntity toEntity(Project domain) {
        List<UserEntity> members = domain.getMembers().stream()
                .map(UserMapper::toEntity).toList();

        return ProjectEntity.builder()
                .title(domain.getTitle())
                .description(domain.getDescription())
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .stacks(domain.getStacks())
                .members(members)
                .leader(UserMapper.toEntity(domain.getLeader()))
                .build();
    }
    public static ProjectEntity updateEntity(ProjectEntity entity, Project domain) {
        List<UserEntity> members = domain.getMembers().stream()
                .map(UserMapper::toEntity).toList();

        return ProjectEntity.builder()
                .title(domain.getTitle())
                .description(domain.getDescription())
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .stacks(domain.getStacks())
                .members(members)
                .leader(UserMapper.toEntity(domain.getLeader()))
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
