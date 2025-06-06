package com.starmix.checkmate.application.service;

import com.starmix.checkmate.adapter.in.sse.common.SseEmitterManager;
import com.starmix.checkmate.adapter.in.sse.web.notification.response.NotificationResponse;
import com.starmix.checkmate.application.port.out.persistence.*;
import com.starmix.checkmate.domain.notification.Notification;
import com.starmix.checkmate.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationPersistencePort notificationPersistencePort;
    private final JwtUtil jwtUtil;
    private final SseEmitterManager sseEmitterManager;

    public void getNotifications(String projectId) {
        String userId = jwtUtil.extractUser().getUserId();
        List<Notification> notifications = notificationPersistencePort.findByUserIdAndProjectId(userId, projectId);
        List<NotificationResponse> response = notifications.stream().map(NotificationResponse::from).toList();
        sseEmitterManager.sendEventTo(userId, "get-notifications", response);
    }

    public void addNotifications(Notification notification) {
        notificationPersistencePort.save(notification);
        sseEmitterManager.sendEventTo(notification.getUserId(), "new-notification", notification);
    }
}