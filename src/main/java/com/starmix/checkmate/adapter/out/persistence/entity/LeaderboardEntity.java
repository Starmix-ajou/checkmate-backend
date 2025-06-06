package com.starmix.checkmate.adapter.out.persistence.entity;

import com.starmix.checkmate.adapter.in.rest.common.response.statistics.ProjectDailyScrumStatistics;
import com.starmix.checkmate.adapter.in.rest.common.response.statistics.ProjectReviewStatistics;
import com.starmix.checkmate.adapter.in.rest.common.response.statistics.ProjectTaskStatistics;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Getter
@SuperBuilder
@Document(collection = "leaderboard")
@NoArgsConstructor
public class LeaderboardEntity extends BaseEntity {
    LocalDate timestamp;
    List<ProjectTaskStatistics> taskStatistics;
    List<ProjectDailyScrumStatistics> dailyScrumStatistics;
    List<ProjectReviewStatistics> reviewStatistics;
}