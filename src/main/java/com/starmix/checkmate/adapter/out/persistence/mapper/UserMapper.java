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
                .id(userEntity.getId())
                .createdAt(userEntity.getCreatedAt())
                .updatedAt(userEntity.getUpdatedAt())
                .build();
    }

    public static UserEntity toEntity(User user) {
        return UserEntity.builder()
                .name(user.getName())
                .email(user.getEmail())
                .profiles(user.getProfiles())
                .role(user.getRole())
                .build();
    }
    public static UserEntity updateEntity(UserEntity entity, User domain) {
        return UserEntity.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .name(domain.getName())
                .email(domain.getEmail())
                .profiles(domain.getProfiles())
                .role(domain.getRole())
                .build();
    }
}
