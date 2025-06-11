package com.starmix.checkmate.adapter.out.persistence.mapper;

import com.starmix.checkmate.adapter.in.rest.common.response.statistics.ProjectDailyScrumStatistics;
import com.starmix.checkmate.adapter.in.rest.common.response.statistics.ProjectReviewStatistics;
import com.starmix.checkmate.adapter.in.rest.common.response.statistics.ProjectTaskStatistics;
import com.starmix.checkmate.adapter.out.persistence.entity.LeaderboardEntity;
import com.starmix.checkmate.domain.leaderboard.Leaderboard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class LeaderboardMapperTest {

    @Test
    @DisplayName("LeaderboardEntity -> Leaderboard 도메인 변환 테스트")
    void toDomainTest() {
        // given
        LocalDate today = LocalDate.now();
        
        ProjectTaskStatistics taskStat1 = mock(ProjectTaskStatistics.class);
        ProjectTaskStatistics taskStat2 = mock(ProjectTaskStatistics.class);
        List<ProjectTaskStatistics> taskStatsList = Arrays.asList(taskStat1, taskStat2);
        
        ProjectDailyScrumStatistics dailyScrumStat1 = mock(ProjectDailyScrumStatistics.class);
        ProjectDailyScrumStatistics dailyScrumStat2 = mock(ProjectDailyScrumStatistics.class);
        List<ProjectDailyScrumStatistics> dailyScrumStatsList = Arrays.asList(dailyScrumStat1, dailyScrumStat2);
        
        ProjectReviewStatistics reviewStat1 = mock(ProjectReviewStatistics.class);
        ProjectReviewStatistics reviewStat2 = mock(ProjectReviewStatistics.class);
        List<ProjectReviewStatistics> reviewStatsList = Arrays.asList(reviewStat1, reviewStat2);

        LeaderboardEntity leaderboardEntity = LeaderboardEntity.builder()
                .id("leaderboard-123")
                .timestamp(today)
                .taskStatistics(taskStatsList)
                .dailyScrumStatistics(dailyScrumStatsList)
                .reviewStatistics(reviewStatsList)
                .build();

        // when
        Leaderboard leaderboard = LeaderboardMapper.toDomain(leaderboardEntity);

        // then
        assertThat(leaderboard).isNotNull();
        assertThat(leaderboard.getLeaderboardId()).isEqualTo(leaderboardEntity.getId());
        assertThat(leaderboard.getTimestamp()).isEqualTo(leaderboardEntity.getTimestamp());
        assertThat(leaderboard.getTaskStatistics()).isEqualTo(leaderboardEntity.getTaskStatistics());
        assertThat(leaderboard.getDailyScrumStatistics()).isEqualTo(leaderboardEntity.getDailyScrumStatistics());
        assertThat(leaderboard.getReviewStatistics()).isEqualTo(leaderboardEntity.getReviewStatistics());
    }

    @Test
    @DisplayName("Leaderboard 도메인 -> LeaderboardEntity 변환 테스트")
    void toEntityTest() {
        // given
        LocalDate today = LocalDate.now();
        
        ProjectTaskStatistics taskStat1 = mock(ProjectTaskStatistics.class);
        ProjectTaskStatistics taskStat2 = mock(ProjectTaskStatistics.class);
        List<ProjectTaskStatistics> taskStatsList = Arrays.asList(taskStat1, taskStat2);
        
        ProjectDailyScrumStatistics dailyScrumStat1 = mock(ProjectDailyScrumStatistics.class);
        ProjectDailyScrumStatistics dailyScrumStat2 = mock(ProjectDailyScrumStatistics.class);
        List<ProjectDailyScrumStatistics> dailyScrumStatsList = Arrays.asList(dailyScrumStat1, dailyScrumStat2);
        
        ProjectReviewStatistics reviewStat1 = mock(ProjectReviewStatistics.class);
        ProjectReviewStatistics reviewStat2 = mock(ProjectReviewStatistics.class);
        List<ProjectReviewStatistics> reviewStatsList = Arrays.asList(reviewStat1, reviewStat2);

        Leaderboard leaderboard = Leaderboard.builder()
                .leaderboardId("leaderboard-456")
                .timestamp(today)
                .taskStatistics(taskStatsList)
                .dailyScrumStatistics(dailyScrumStatsList)
                .reviewStatistics(reviewStatsList)
                .build();

        // when
        LeaderboardEntity leaderboardEntity = LeaderboardMapper.toEntity(leaderboard);

        // then
        assertThat(leaderboardEntity).isNotNull();
        assertThat(leaderboardEntity.getId()).isEqualTo(leaderboard.getLeaderboardId());
        assertThat(leaderboardEntity.getTimestamp()).isEqualTo(leaderboard.getTimestamp());
        assertThat(leaderboardEntity.getTaskStatistics()).isEqualTo(leaderboard.getTaskStatistics());
        assertThat(leaderboardEntity.getDailyScrumStatistics()).isEqualTo(leaderboard.getDailyScrumStatistics());
        assertThat(leaderboardEntity.getReviewStatistics()).isEqualTo(leaderboard.getReviewStatistics());
    }

    @Test
    @DisplayName("비어있는 통계 리스트 변환 테스트")
    void emptyStatisticsListsTest() {
        // given
        Leaderboard leaderboardWithEmptyLists = Leaderboard.builder()
                .leaderboardId("leaderboard-empty")
                .timestamp(LocalDate.now())
                .taskStatistics(List.of())
                .dailyScrumStatistics(List.of())
                .reviewStatistics(List.of())
                .build();

        LeaderboardEntity entityWithEmptyLists = LeaderboardEntity.builder()
                .id("entity-empty")
                .timestamp(LocalDate.now())
                .taskStatistics(List.of())
                .dailyScrumStatistics(List.of())
                .reviewStatistics(List.of())
                .build();

        // when
        LeaderboardEntity resultEntity = LeaderboardMapper.toEntity(leaderboardWithEmptyLists);
        Leaderboard resultDomain = LeaderboardMapper.toDomain(entityWithEmptyLists);

        // then
        assertThat(resultEntity.getTaskStatistics()).isEmpty();
        assertThat(resultEntity.getDailyScrumStatistics()).isEmpty();
        assertThat(resultEntity.getReviewStatistics()).isEmpty();

        assertThat(resultDomain.getTaskStatistics()).isEmpty();
        assertThat(resultDomain.getDailyScrumStatistics()).isEmpty();
        assertThat(resultDomain.getReviewStatistics()).isEmpty();
    }

    @Test
    @DisplayName("ID 변경 후 변환 테스트")
    void changeIdTest() {
        // given
        Leaderboard leaderboard = Leaderboard.builder()
                .leaderboardId("old-id")
                .timestamp(LocalDate.now())
                .build();

        // ID 변경
        leaderboard.changeId("new-id");

        // when
        LeaderboardEntity entity = LeaderboardMapper.toEntity(leaderboard);

        // then
        assertThat(entity.getId()).isEqualTo("new-id");
    }

    @Test
    @DisplayName("null 리더보드 변환 테스트")
    void nullLeaderboardTest() {
        // then
        assertThatThrownBy(() -> LeaderboardMapper.toDomain(null))
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> LeaderboardMapper.toEntity(null))
                .isInstanceOf(NullPointerException.class);
    }
}