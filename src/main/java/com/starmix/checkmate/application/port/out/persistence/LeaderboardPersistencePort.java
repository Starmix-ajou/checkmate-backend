package com.starmix.checkmate.application.port.out.persistence;

import com.starmix.checkmate.adapter.out.persistence.entity.LeaderboardEntity;
import com.starmix.checkmate.domain.leaderboard.Leaderboard;

import java.time.LocalDate;

public interface LeaderboardPersistencePort {
    void save(Leaderboard leaderboard);
    LeaderboardEntity findLatest();
    LeaderboardEntity findByTimestamp(LocalDate timestamp);
}
