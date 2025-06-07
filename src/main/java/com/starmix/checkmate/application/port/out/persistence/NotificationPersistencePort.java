package com.starmix.checkmate.application.port.out.persistence;

import com.starmix.checkmate.domain.notification.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationPersistencePort {
    List<Notification> findByUserIdAndProjectId(String userId, String projectId);
    void save(Notification notification);
    void delete(String notificationId);
    Optional<Notification> findById(String notificationId);
}
