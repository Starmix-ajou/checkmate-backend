package com.starmix.checkmate.adapter.out.persistence.mongo;

import com.starmix.checkmate.adapter.out.persistence.entity.LeaderboardEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaderBoardMongoRepository extends MongoRepository<LeaderboardEntity, String> {
    List<LeaderboardEntity> findAllByTimestamp(LocalDate timestamp, Sort sort);
}
