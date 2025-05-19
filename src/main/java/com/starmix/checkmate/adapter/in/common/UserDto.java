package com.starmix.checkmate.adapter.in.common;

import com.starmix.checkmate.domain.user.Profile;
import com.starmix.checkmate.domain.user.Role;
import com.starmix.checkmate.domain.user.User;
import lombok.Builder;

@Builder
public record UserDto(
        String userId,
        String email,
        String profileImageUrl,
        Profile profile,
        Role role
) {
    public static UserDto fromDomain(User user, String projectId) {
        return UserDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .profileImageUrl(user.getProfileImageUrl())
                .profile(user.getProfileByProjectId(projectId))
                .build();
    }
}
