package com.starmix.checkmate.adapter.out.persistence.mongo;

import com.starmix.checkmate.adapter.out.persistence.entity.NotificationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationMongoRepository extends MongoRepository<NotificationEntity, String> {
    List<NotificationEntity> findByUserId(String userId);
    List<NotificationEntity> findByUserIdAndProjectId(String userId, String projectId);
}
