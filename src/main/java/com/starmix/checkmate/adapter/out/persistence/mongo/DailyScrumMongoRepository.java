package com.starmix.checkmate.adapter.out.persistence.mongo;

import com.starmix.checkmate.adapter.out.persistence.entity.DailyScrumEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyScrumMongoRepository extends MongoRepository<DailyScrumEntity, String> {
    List<DailyScrumEntity> findAllByProjectId(String projectId);
    Optional<DailyScrumEntity> findByTimestamp(LocalDate timestamp);
}
