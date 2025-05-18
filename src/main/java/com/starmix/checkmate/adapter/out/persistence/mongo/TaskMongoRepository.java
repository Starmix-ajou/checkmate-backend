package com.starmix.checkmate.adapter.out.persistence.mongo;

import com.starmix.checkmate.adapter.out.persistence.entity.TaskEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskMongoRepository extends MongoRepository<TaskEntity, String> {
    List<TaskEntity> findByAssignee_Id(String assigneeId);
    List<TaskEntity> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate endDate, LocalDate startDate);
}
