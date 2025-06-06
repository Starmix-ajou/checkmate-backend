package com.starmix.checkmate.domain.leaderboard;

import com.starmix.checkmate.adapter.in.rest.common.response.statistics.ProjectDailyScrumStatistics;
import com.starmix.checkmate.adapter.in.rest.common.response.statistics.ProjectReviewStatistics;
import com.starmix.checkmate.adapter.in.rest.common.response.statistics.ProjectStatisticsResponse;
import com.starmix.checkmate.adapter.in.rest.common.response.statistics.ProjectTaskStatistics;
import com.starmix.checkmate.domain.project.Project;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Builder
public class Leaderboard {
    private String leaderboardId;
    private LocalDate timestamp;
    private List<ProjectTaskStatistics> taskStatistics;
    private List<ProjectDailyScrumStatistics> dailyScrumStatistics;
    private List<ProjectReviewStatistics> reviewStatistics;

    public static Leaderboard createFromProjectStatistics(List<Project> projects, List<ProjectStatisticsResponse> projectStatistics) {
        List<ProjectTaskStatistics> taskStatisticsList = new ArrayList<>();
        List<ProjectDailyScrumStatistics> dailyScrumStatisticsList = new ArrayList<>();
        List<ProjectReviewStatistics> reviewStatisticsList = new ArrayList<>();

        projectStatistics.forEach(projectStat -> {
           Project project = projects.stream()
                   .filter(proj -> proj.getProjectId().equals(projectStat.projectId()))
                   .findFirst().orElse(null);

            taskStatisticsList.add(ProjectTaskStatistics.from(project, projectStat.taskStatistics()));
            dailyScrumStatisticsList.add(ProjectDailyScrumStatistics.from(project, projectStat.dailyScrumStatistics()));
            reviewStatisticsList.add(ProjectReviewStatistics.from(project, projectStat.reviewStatistics()));

        });

        taskStatisticsList.sort(Comparator.comparingDouble(stats -> -stats.statistics().doneRate()));
        dailyScrumStatisticsList.sort(Comparator.comparingDouble(stats -> -stats.statistics().doneRate()));
        reviewStatisticsList.sort(Comparator.comparingDouble(stats -> -stats.statistics().doneRate()));

        return Leaderboard.builder()
                .timestamp(LocalDate.now())
                .taskStatistics(taskStatisticsList)
                .dailyScrumStatistics(dailyScrumStatisticsList)
                .reviewStatistics(reviewStatisticsList)
                .build();
    }

    public void changeId(String leaderboardId) {
        this.leaderboardId = leaderboardId;
    }
}