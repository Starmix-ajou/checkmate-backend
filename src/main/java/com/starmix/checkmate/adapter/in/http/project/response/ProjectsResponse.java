package com.starmix.checkmate.adapter.in.http.project.response;

import com.starmix.checkmate.domain.project.Project;
import com.starmix.checkmate.domain.user.Profile;
import com.starmix.checkmate.domain.user.User;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record ProjectsResponse(
        String projectId,
        String projectTitle,
        String projectImageUrl,
        Profile profile,
        LocalDate startDate,
        LocalDate endDate,
        List<User> members,
        User leader
) {
    public static ProjectsResponse fromDomain(Project project, Profile profile) {
        return ProjectsResponse.builder()
                .projectTitle(project.getTitle())
                .projectImageUrl(project.getImageUrl())
                .projectId(project.getProjectId())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .members(project.getMembers())
                .leader(project.getLeader())
                .profile(profile)
                .build();
    }
}
