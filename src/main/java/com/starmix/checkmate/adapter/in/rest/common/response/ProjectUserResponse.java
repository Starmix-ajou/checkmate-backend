package com.starmix.checkmate.adapter.in.rest.common.response;

import com.starmix.checkmate.adapter.in.rest.common.UserDto;
import com.starmix.checkmate.domain.user.User;
import lombok.Builder;

import java.util.List;

@Builder
public record ProjectUserResponse(
        List<UserDto> members,
        UserDto leader,
        UserDto productManager
) {
    public static ProjectUserResponse fromDomain(
            List<User> members, User leader,
            User productManager, String projectId
    ) {
        List<UserDto> memberDtos = members.stream().map(
                member -> UserDto.fromDomain(member, projectId)
        ).toList();

        return ProjectUserResponse.builder()
                .members(memberDtos)
                .leader(UserDto.fromDomain(leader, projectId))
                .productManager(UserDto.fromDomain(productManager, projectId))
                .build();
    }
}
