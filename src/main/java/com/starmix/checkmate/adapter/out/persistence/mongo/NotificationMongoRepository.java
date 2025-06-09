package com.starmix.checkmate.adapter.out.persistence.mongo;

import com.starmix.checkmate.adapter.out.persistence.entity.NotificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationMongoRepository extends MongoRepository<NotificationEntity, String> {
    Page<NotificationEntity> findByUserId(String userId, Pageable pageable);
    Page<NotificationEntity> findByUserIdAndProject_Id(String userId, String projectId, Pageable pageable);
    Integer countByUserIdAndProject_IdAndIsReadFalse(String userId, String projectId);
    Integer countByUserIdAndIsReadFalse(String userId);
}
