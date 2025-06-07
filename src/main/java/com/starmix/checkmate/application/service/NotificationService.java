package com.starmix.checkmate.application.service;

import com.starmix.checkmate.adapter.in.sse.common.SseEmitterManager;
import com.starmix.checkmate.application.port.out.persistence.*;
import com.starmix.checkmate.domain.notification.Notification;
import com.starmix.checkmate.global.exception.CustomException;
import com.starmix.checkmate.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationPersistencePort notificationPersistencePort;
    private final JwtUtil jwtUtil;
    private final SseEmitterManager sseEmitterManager;

    public List<Notification> getNotifications(String projectId) {
        String userId = jwtUtil.extractUser().getUserId();
        return notificationPersistencePort.findByUserIdAndProjectId(userId, projectId);
    }

    public void addNotifications(Notification notification) {
        notificationPersistencePort.save(notification);
        sseEmitterManager.sendEventTo(notification.getUserId(), "new-notification", notification);
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
}