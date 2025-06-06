package com.starmix.checkmate.adapter.out.persistence.adapter;

import com.starmix.checkmate.adapter.out.persistence.entity.NotificationEntity;
import com.starmix.checkmate.adapter.out.persistence.mapper.NotificationMapper;
import com.starmix.checkmate.adapter.out.persistence.mongo.NotificationMongoRepository;
import com.starmix.checkmate.application.port.out.persistence.NotificationPersistencePort;
import com.starmix.checkmate.domain.notification.Notification;
import com.starmix.checkmate.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationPersistenceAdapter implements NotificationPersistencePort {

    private final NotificationMongoRepository notificationMongoRepository;

    @Override
    public List<Notification> findByUserIdAndProjectId(String userId, String projectId) {
        try {
            List<NotificationEntity> notificationEntities;
            if(projectId != null && !projectId.isEmpty()) {
                notificationEntities = notificationMongoRepository.findByUserIdAndProject_Id(userId, projectId);
            } else {
                notificationEntities = notificationMongoRepository.findByUserId(userId);
            }

            return notificationEntities.stream().map(NotificationMapper::toDomain).toList();
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void save(Notification notification) {
        try {
            NotificationEntity notificationEntity = NotificationMapper.toEntity(notification);
            notificationMongoRepository.save(notificationEntity);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
