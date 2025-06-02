package com.starmix.checkmate.adapter.out.persistence.mapper;

import com.starmix.checkmate.adapter.out.persistence.entity.UserEntity;
import com.starmix.checkmate.domain.user.User;

public class UserMapper {

    public static User toDomain(UserEntity userEntity) {
        return User.builder()
                .userId(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .profiles(userEntity.getProfiles())
                .profileImageUrl(userEntity.getProfileImageUrl())
                .build();
    }

    public static UserEntity toEntity(User user) {
        return UserEntity.builder()
                .id(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .profiles(user.getProfiles())
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }
}
