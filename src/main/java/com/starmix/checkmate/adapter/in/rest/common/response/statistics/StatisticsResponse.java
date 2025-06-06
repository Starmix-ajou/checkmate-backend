package com.starmix.checkmate.adapter.in.rest.common.response.statistics;

import com.starmix.checkmate.adapter.out.persistence.entity.LeaderboardEntity;
import lombok.Builder;

import java.util.List;

@Builder
public record StatisticsResponse(
        List<ProjectTaskStatistics> taskStatistics,
        List<ProjectDailyScrumStatistics> dailyScrumStatistics,
        List<ProjectReviewStatistics> reviewStatistics
) {
    public static StatisticsResponse from(
            List<ProjectTaskStatistics> taskStatistics,
            List<ProjectDailyScrumStatistics> dailyScrumStatistics,
            List<ProjectReviewStatistics> projectReviewStatistics
    ) {
        return StatisticsResponse.builder()
                .taskStatistics(taskStatistics)
                .dailyScrumStatistics(dailyScrumStatistics)
                .reviewStatistics(projectReviewStatistics)
                .build();
    }

    public static StatisticsResponse fromLeaderBoardEntity(LeaderboardEntity entity) {
        if(entity == null) {return null;}

        return StatisticsResponse.builder()
                .taskStatistics(entity.getTaskStatistics())
                .dailyScrumStatistics(entity.getDailyScrumStatistics())
                .reviewStatistics(entity.getReviewStatistics())
                .build();
    }
}
