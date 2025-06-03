package com.starmix.checkmate.application.port.out.persistence;

import com.starmix.checkmate.domain.notification.Notification;

import java.util.List;

public interface NotificationPersistencePort {
    List<Notification> findByUserIdAndProjectId(String userId, String projectId);
    void save(Notification notification);
}
