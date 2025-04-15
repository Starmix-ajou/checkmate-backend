package com.starmix.checkmate.adapter.out.persistence.mongo;

import com.starmix.checkmate.adapter.out.persistence.entity.MeetingEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingMongoRepository extends MongoRepository<MeetingEntity, String> {
    List<MeetingEntity> findAllByProjectId(String projectId);
}
