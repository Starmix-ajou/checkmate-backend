package com.starmix.checkmate.application.port.out.persistence;

import com.starmix.checkmate.domain.notification.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface NotificationPersistencePort {
    Page<Notification> findByUserIdAndProjectId(String userId, String projectId, Pageable pageable);
    void save(Notification notification);
    void delete(String notificationId);
    Optional<Notification> findById(String notificationId);
}
