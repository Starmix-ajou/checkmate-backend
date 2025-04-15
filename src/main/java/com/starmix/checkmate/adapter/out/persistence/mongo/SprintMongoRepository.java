package com.starmix.checkmate.adapter.out.persistence.mongo;

import com.starmix.checkmate.adapter.out.persistence.entity.SprintEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SprintMongoRepository extends MongoRepository<SprintEntity, String> {
    List<SprintEntity> findAllByProjectId(String projectId);
}
