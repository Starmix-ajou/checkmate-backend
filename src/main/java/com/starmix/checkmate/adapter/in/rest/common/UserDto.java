package com.starmix.checkmate.adapter.in.rest.common;

import com.starmix.checkmate.domain.user.Profile;
import com.starmix.checkmate.domain.user.User;
import lombok.Builder;

@Builder
public record UserDto(
        String userId,
        String name,
        String email,
        String profileImageUrl,
        Profile profile
) {
    public static UserDto fromDomain(User user, String projectId) {
        if(user == null) return null;
        return UserDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .profileImageUrl(user.getProfileImageUrl())
                .profile(user.getProfileByProjectId(projectId))
                .build();
    }
}
