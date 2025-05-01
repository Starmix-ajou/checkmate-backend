package com.starmix.checkmate.adapter.in.sse.project.request;

import com.starmix.checkmate.adapter.in.common.UserDto;
import com.starmix.checkmate.domain.project.Project;
import com.starmix.checkmate.domain.user.Profile;
import com.starmix.checkmate.domain.user.User;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record CreateFeatureDefinitionRequest(
        String title,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        List<UserDto> members,
        String definitionUrl
) {
    public Project toDomain(User leader, List<User> members) {
        String projectId = UUID.randomUUID().toString();

        this.members.forEach(
                userDto -> {
                    Profile profile = userDto.profile();
                    profile.init(projectId);

                    if(userDto.email().equals(leader.getEmail())) {
                        leader.addProfile(profile);
                    }
                }
        );

        for (UserDto userDto : this.members) {
            Profile profile = userDto.profile();
            profile.init(projectId);

            members.stream()
                    .filter(member -> member.getEmail().equals(userDto.email()))
                    .findFirst()
                    .ifPresent(member -> member.addProfile(profile));
        }

        return Project.builder()
                .projectId(projectId)
                .title(this.title)
                .description(this.description)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .leader(leader)
                .members(members)
                .build();
    }
}