package com.starmix.checkmate.domain;

import com.starmix.checkmate.adapter.in.rest.common.response.statistics.*;
import com.starmix.checkmate.domain.leaderboard.Leaderboard;
import com.starmix.checkmate.domain.project.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LeaderboardTest {
    private List<Project> projects;
    private List<ProjectStatisticsResponse> projectStatistics;

    @BeforeEach
    void setUp() {
        // 프로젝트 설정
        projects = List.of(
                Project.builder().projectId("project-1").title("Project 1").build(),
                Project.builder().projectId("project-2").title("Project 2").build(),
                Project.builder().projectId("project-3").title("Project 3").build()
        );

        // 통계 데이터 설정
        projectStatistics = List.of(
                createProjectStatistics("project-1", 0.8, 0.7, 0.9),
                createProjectStatistics("project-2", 0.6, 0.9, 0.5),
                createProjectStatistics("project-3", 0.9, 0.5, 0.7)
        );
    }

    @Test
    @DisplayName("프로젝트 통계로부터 리더보드 생성 테스트")
    void createFromProjectStatistics() {
        // when
        Leaderboard leaderboard = Leaderboard.createFromProjectStatistics(projects, projectStatistics);

        // then
        assertThat(leaderboard.getTimestamp()).isEqualTo(LocalDate.now());
        
        // Task 통계 정렬 검증 (내림차순)
        assertThat(leaderboard.getTaskStatistics())
                .hasSize(3)
                .extracting(stats -> stats.statistics().doneRate())
                .containsExactly(0.9, 0.8, 0.6);

        // Daily Scrum 통계 정렬 검증 (내림차순)
        assertThat(leaderboard.getDailyScrumStatistics())
                .hasSize(3)
                .extracting(stats -> stats.statistics().doneRate())
                .containsExactly(0.9, 0.7, 0.5);

        // Review 통계 정렬 검증 (내림차순)
        assertThat(leaderboard.getReviewStatistics())
                .hasSize(3)
                .extracting(stats -> stats.statistics().doneRate())
                .containsExactly(0.9, 0.7, 0.5);
    }

    @Test
    @DisplayName("리더보드 ID 변경 테스트")
    void changeId() {
        // given
        Leaderboard leaderboard = Leaderboard.builder()
                .leaderboardId("old-id")
                .build();

        // when
        leaderboard.changeId("new-id");

        // then
        assertThat(leaderboard.getLeaderboardId()).isEqualTo("new-id");
    }

    private ProjectStatisticsResponse createProjectStatistics(String projectId, double taskRate, double scrumRate, double reviewRate) {
        return new ProjectStatisticsResponse(
                projectId,
                new TaskStatistics(10, 5, 5, 20, taskRate),
                new DailyScrumStatistics(10, 5, scrumRate),
                new ReviewStatistics(10, 5, reviewRate)
        );
    }
}