package com.starmix.checkmate.adapter.out.persistence.adapter;

import com.starmix.checkmate.adapter.out.persistence.entity.LeaderboardEntity;
import com.starmix.checkmate.adapter.out.persistence.mapper.LeaderboardMapper;
import com.starmix.checkmate.adapter.out.persistence.mongo.LeaderBoardMongoRepository;
import com.starmix.checkmate.application.port.out.persistence.LeaderboardPersistencePort;
import com.starmix.checkmate.domain.leaderboard.Leaderboard;
import com.starmix.checkmate.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@RequiredArgsConstructor
@Component
public class LeaderboardPersistenceAdapter implements LeaderboardPersistencePort {

    private final LeaderBoardMongoRepository leaderBoardMongoRepository;

    @Override
    public void save(Leaderboard leaderboard) {
        try {
            LeaderboardEntity leaderboardEntity = LeaderboardMapper.toEntity(leaderboard);
            leaderBoardMongoRepository.save(leaderboardEntity);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public LeaderboardEntity findLatest() {
        try {
            return leaderBoardMongoRepository.findAll(Sort.by(Sort.Direction.DESC, "timestamp"))
                    .stream().findFirst().orElse(null);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public LeaderboardEntity findByTimestamp(LocalDate timestamp) {
        try {
            return leaderBoardMongoRepository.findAllByTimestamp(
                            timestamp, Sort.by(Sort.Direction.DESC, "timestamp")
                    )
                    .stream().findFirst().orElse(null);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
