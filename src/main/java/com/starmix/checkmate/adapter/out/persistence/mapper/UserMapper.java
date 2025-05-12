package com.starmix.checkmate.adapter.out.persistence.mapper;

import com.starmix.checkmate.adapter.out.persistence.entity.UserEntity;
import com.starmix.checkmate.domain.user.User;

import java.time.LocalDateTime;

public class UserMapper {

    public static User toDomain(UserEntity userEntity) {
        return User.builder()
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .profiles(userEntity.getProfiles())
                .role(userEntity.getRole())
                .profileImageUrl(userEntity.getProfileImageUrl())
                .userId(userEntity.getId())
                .build();
    }

    public static UserEntity toEntity(User user) {
        return UserEntity.builder()
                .id(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .profiles(user.getProfiles())
                .role(user.getRole())
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }
}
