package com.starmix.checkmate.adapter.in.rest.web.notification.response;

import com.starmix.checkmate.adapter.in.rest.web.project.response.ProjectResponse;
import com.starmix.checkmate.domain.notification.Notification;
import lombok.Builder;

@Builder
public record NotificationResponse(
        String notificationId,
        String title,
        String description,
        String targetId,
        Boolean isRead,
        ProjectResponse project
) {
    public static NotificationResponse from(Notification notification) {
        return NotificationResponse.builder()
                .notificationId(notification.getNotificationId())
                .title(notification.getTitle())
                .description(notification.getDescription())
                .targetId(notification.getTargetId())
                .isRead(notification.getIsRead())
                .project(ProjectResponse.fromDomain(notification.getProject()))
                .build();
    }
}
