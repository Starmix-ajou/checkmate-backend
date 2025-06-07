package com.starmix.checkmate.adapter.out.persistence.adapter;

import com.starmix.checkmate.adapter.out.persistence.entity.NotificationEntity;
import com.starmix.checkmate.adapter.out.persistence.mapper.NotificationMapper;
import com.starmix.checkmate.adapter.out.persistence.mongo.NotificationMongoRepository;
import com.starmix.checkmate.application.port.out.persistence.NotificationPersistencePort;
import com.starmix.checkmate.domain.notification.Notification;
import com.starmix.checkmate.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NotificationPersistenceAdapter implements NotificationPersistencePort {

    private final NotificationMongoRepository notificationMongoRepository;

    @Override
    public Page<Notification> findByUserIdAndProjectId(String userId, String projectId, Pageable pageable) {
        try {
            Page<NotificationEntity> notificationEntities;
            if(projectId != null && !projectId.isEmpty()) {
                notificationEntities = notificationMongoRepository.findByUserIdAndProject_Id(userId, projectId, pageable);
            } else {
                notificationEntities = notificationMongoRepository.findByUserId(userId, pageable);
            }

            return notificationEntities.map(NotificationMapper::toDomain);
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

    @Override
    public void delete(String notificationId) {
        try {
            notificationMongoRepository.deleteById(notificationId);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Optional<Notification> findById(String notificationId) {
        try {
            Optional<NotificationEntity> notificationEntity = notificationMongoRepository.findById(notificationId);
            return notificationEntity.map(NotificationMapper::toDomain);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Integer countByUserIdAndProjectId(String userId, String projectId) {
        try {
            Integer count;
            if(projectId != null && !projectId.isEmpty()) {
                count = notificationMongoRepository.countByUserIdAndProject_Id(userId, projectId);
            } else {
                count = notificationMongoRepository.countByUserId(userId);
            }
            return count;
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
