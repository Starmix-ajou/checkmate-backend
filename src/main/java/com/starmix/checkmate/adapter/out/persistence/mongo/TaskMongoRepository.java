package com.starmix.checkmate.adapter.out.persistence.mongo;

import com.starmix.checkmate.adapter.out.persistence.entity.TaskEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskMongoRepository extends MongoRepository<TaskEntity, String> {
    List<TaskEntity> findByEpic_Id(String epicId);
}
