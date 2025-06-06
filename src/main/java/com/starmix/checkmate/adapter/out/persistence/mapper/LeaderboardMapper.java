package com.starmix.checkmate.adapter.out.persistence.mapper;

import com.starmix.checkmate.adapter.out.persistence.entity.LeaderboardEntity;
import com.starmix.checkmate.domain.leaderboard.Leaderboard;

public class LeaderboardMapper {

    public static Leaderboard toDomain(LeaderboardEntity entity) {
        return Leaderboard.builder()
                .timestamp(entity.getTimestamp())
                .taskStatistics(entity.getTaskStatistics())
                .dailyScrumStatistics(entity.getDailyScrumStatistics())
                .reviewStatistics(entity.getReviewStatistics())
                .build();
    }

    public static LeaderboardEntity toEntity(Leaderboard domain) {
        return LeaderboardEntity.builder()
                .timestamp(domain.getTimestamp())
                .taskStatistics(domain.getTaskStatistics())
                .dailyScrumStatistics(domain.getDailyScrumStatistics())
                .reviewStatistics(domain.getReviewStatistics())
                .build();
    }
}
