package com.starmix.checkmate.adapter.in.rest.web.project.response;

import com.starmix.checkmate.adapter.in.rest.web.common.ProjectBriefDto;
import com.starmix.checkmate.domain.project.Project;
import com.starmix.checkmate.domain.user.Profile;
import com.starmix.checkmate.domain.user.User;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record ProjectsResponse(
        ProjectBriefDto project,
        Profile profile,
        LocalDate startDate,
        LocalDate endDate,
        List<User> members,
        User leader
) {
    public static ProjectsResponse fromDomain(Project project, Profile profile) {
        return ProjectsResponse.builder()
                .project(ProjectBriefDto.fromDomain(project))
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .members(project.getMembers())
                .leader(project.getLeader())
                .profile(profile)
                .build();
    }

    public static List<ProjectsResponse> toProjectResponse(User user, List<Project> projects) {
        return projects.stream().map(
                project -> ProjectsResponse.fromDomain(
                        project, user.getProfileByProjectId(project.getProjectId())
                )
        ).toList();
    }
}
