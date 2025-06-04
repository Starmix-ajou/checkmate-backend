package com.starmix.checkmate.adapter.in.rest.web.project.response;

import com.starmix.checkmate.adapter.in.rest.common.UserDto;
import com.starmix.checkmate.domain.project.Project;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record ProjectResponse(
        String projectId,
        String title,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        List<UserDto> members,
        UserDto leader,
        UserDto productManager,
        String imageUrl
) {
    public static ProjectResponse fromDomain(Project project) {
        return ProjectResponse.builder()
                .projectId(project.getProjectId())
                .title(project.getTitle())
                .description(project.getDescription())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .members(project.getMembers().stream().map(
                        member -> UserDto.fromDomain(member, project.getProjectId())
                ).toList())
                .leader(UserDto.fromDomain(project.getLeader(), project.getProjectId()))
                .productManager(UserDto.fromDomain(project.getProductManager(), project.getProjectId()))
                .imageUrl(project.getImageUrl())
                .build();
    }
}
