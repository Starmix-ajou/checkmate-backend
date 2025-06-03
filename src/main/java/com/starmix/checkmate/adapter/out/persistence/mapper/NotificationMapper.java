package com.starmix.checkmate.adapter.out.persistence.mapper;

import com.starmix.checkmate.adapter.out.persistence.entity.NotificationEntity;
import com.starmix.checkmate.domain.notification.Notification;

public class NotificationMapper {

    public static Notification toDomain(NotificationEntity entity) {
        return Notification.builder()
                .notificationId(entity.getId())
                .userId(entity.getUserId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .targetId(entity.getTargetId())
                .isRead(entity.getIsRead())
                .project(ProjectMapper.toDomain(entity.getProject()))
                .build();
    }

    public static NotificationEntity toEntity(Notification domain) {
        return NotificationEntity.builder()
                .id(domain.getNotificationId())
                .userId(domain.getUserId())
                .title(domain.getTitle())
                .description(domain.getDescription())
                .targetId(domain.getTargetId())
                .isRead(domain.getIsRead())
                .project(ProjectMapper.toEntity(domain.getProject()))
                .build();
    }
}
