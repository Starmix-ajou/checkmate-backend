package com.starmix.checkmate.adapter.in.rest.common.response;

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
    @Builder
    public record ProjectBriefDto(
            String projectId,
            String title,
            String imageUrl,
            Boolean isPremium
    ) {
        public static ProjectBriefDto fromDomain(Project project) {
            return ProjectBriefDto.builder()
                    .projectId(project.getProjectId())
                    .title(project.getTitle())
                    .imageUrl(project.getImageUrl())
                    .isPremium(project.isPremium())
                    .build();
        }
    }

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
