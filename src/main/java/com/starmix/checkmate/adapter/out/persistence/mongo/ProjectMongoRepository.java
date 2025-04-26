package com.starmix.checkmate.adapter.out.persistence.mongo;

import com.starmix.checkmate.adapter.out.persistence.entity.ProjectEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProjectMongoRepository extends MongoRepository<ProjectEntity, String> {
    List<ProjectEntity> findByMembersEmail(String email);
    List<ProjectEntity> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate todayForStartDate, LocalDate todayForEndDate);
    List<ProjectEntity> findByEndDateBefore(LocalDate today);
}