package com.starmix.checkmate.application.service;

import com.starmix.checkmate.adapter.in.sse.common.SseEmitterManager;
import com.starmix.checkmate.adapter.in.sse.common.SseType;
import com.starmix.checkmate.application.port.out.persistence.*;
import com.starmix.checkmate.domain.notification.Notification;
import com.starmix.checkmate.global.exception.CustomException;
import com.starmix.checkmate.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationPersistencePort notificationPersistencePort;
    private final JwtUtil jwtUtil;
    private final SseEmitterManager sseEmitterManager;

    public Page<Notification> getNotifications(String projectId, Integer page, Integer size) {
        String userId = jwtUtil.extractUser().getUserId();
        Pageable pageable = PageRequest.of(
                page == null ? 0 : page,
                size == null ? 10 : size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        return notificationPersistencePort.findByUserIdAndProjectId(userId, projectId, pageable);
    }

    public void addNotifications(Notification notification) {
        notificationPersistencePort.save(notification);
        sseEmitterManager.sendEventTo(SseType.NOTIFICATION, notification.getUserId(), "new-notification", notification);
    }

    public void readNotification(String notificationId) {
        Notification notification = notificationPersistencePort.findById(notificationId)
                .orElseThrow(() -> new CustomException("Notification not found.", HttpStatus.NOT_FOUND));
        notification.read();

        notificationPersistencePort.save(notification);
    }

    public void deleteNotification(String notificationId) {
        notificationPersistencePort.delete(notificationId);
    }

    public Integer getNotificationCount(String projectId) {
        String userId = jwtUtil.extractUser().getUserId();
        return notificationPersistencePort.countByUserIdAndProjectId(userId, projectId);
    }
}