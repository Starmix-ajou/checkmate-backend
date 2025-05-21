package com.starmix.checkmate.adapter.out.persistence.mongo;

import com.starmix.checkmate.adapter.out.persistence.entity.SprintEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SprintMongoRepository extends MongoRepository<SprintEntity, String> {
    List<SprintEntity> findAllByProjectId(String projectId);
    Optional<SprintEntity> findByProjectIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            String projectId, LocalDate startDate, LocalDate endDate
    );
    long countByProjectId(String projectId);
    List<SprintEntity> findByEpics_Id(String epicId);
}
