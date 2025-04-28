package com.starmix.checkmate.adapter.out.persistence.mapper;

import com.starmix.checkmate.adapter.out.persistence.entity.ProjectEntity;
import com.starmix.checkmate.domain.project.Project;
import com.starmix.checkmate.domain.user.User;

import java.time.LocalDateTime;
import java.util.List;

public class ProjectMapper {

    public static Project toDomain(ProjectEntity entity, User leader, List<User> members) {
        return Project.builder()
                .title(entity.getTitle())
                .description(entity.getDescription())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .stacks(entity.getStacks())
                .members(members)
                .leader(leader)
                .imageUrl(entity.getImageUrl())
                .projectId(entity.getId())
                .build();
    }

    public static ProjectEntity toEntity(Project domain) {
        List<String> memberIds = domain.getMembers().stream()
                .map(User::getUserId).toList();

        return ProjectEntity.builder()
                .title(domain.getTitle())
                .description(domain.getDescription())
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .stacks(domain.getStacks())
                .memberIds(memberIds)
                .leaderId(domain.getLeader().getUserId())
                .imageUrl(domain.getImageUrl())
                .build();
    }

    public static ProjectEntity updateEntity(ProjectEntity entity, Project domain) {
        List<String> memberIds = domain.getMembers().stream()
                .map(User::getUserId).toList();

        return ProjectEntity.builder()
                .title(domain.getTitle())
                .description(domain.getDescription())
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .stacks(domain.getStacks())
                .memberIds(memberIds)
                .leaderId(domain.getLeader().getUserId())
                .imageUrl(domain.getImageUrl())
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
