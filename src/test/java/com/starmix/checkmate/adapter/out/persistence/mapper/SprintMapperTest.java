package com.starmix.checkmate.adapter.out.persistence.mapper;

import com.starmix.checkmate.adapter.out.persistence.entity.EpicEntity;
import com.starmix.checkmate.adapter.out.persistence.entity.SprintEntity;
import com.starmix.checkmate.domain.epic.Epic;
import com.starmix.checkmate.domain.sprint.Sprint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SprintMapperTest {

    @Test
    @DisplayName("SprintEntity -> Sprint 도메인 변환 테스트")
    void toDomainTest() {
        // given
        LocalDate now = LocalDate.now();

        EpicEntity epic = EpicEntity.builder()
                .id("epic-456")
                .title("새 에픽")
                .description("새 에픽 설명")
                .projectId("project-456")
                .featureId("feature-456")
                .build();

        SprintEntity sprintEntity = SprintEntity.builder()
                .id("sprint-123")
                .title("테스트 스프린트")
                .description("스프린트 설명")
                .sequence(1)
                .projectId("project-123")
                .startDate(now)
                .endDate(now.plusDays(14))
                .epics(List.of(epic))
                .build();

        // when
        Sprint sprint = SprintMapper.toDomain(sprintEntity);

        // then
        assertThat(sprint).isNotNull();
        assertThat(sprint.getSprintId()).isEqualTo(sprintEntity.getId());
        assertThat(sprint.getTitle()).isEqualTo(sprintEntity.getTitle());
        assertThat(sprint.getDescription()).isEqualTo(sprintEntity.getDescription());
        assertThat(sprint.getProjectId()).isEqualTo(sprintEntity.getProjectId());
        assertThat(sprint.getStartDate()).isEqualTo(sprintEntity.getStartDate());
        assertThat(sprint.getEndDate()).isEqualTo(sprintEntity.getEndDate());
    }

    @Test
    @DisplayName("Sprint 도메인 -> SprintEntity 변환 테스트")
    void toEntityTest() {
        // given
        LocalDate now = LocalDate.now();

        Epic epic = Epic.builder()
                .epicId("epic-456")
                .title("새 에픽")
                .description("새 에픽 설명")
                .projectId("project-456")
                .featureId("feature-456")
                .build();

        Sprint sprint = Sprint.builder()
                .sprintId("sprint-456")
                .title("새 스프린트")
                .description("새 스프린트 설명")
                .projectId("project-456")
                .startDate(now)
                .endDate(now.plusDays(14))
                .epics(List.of(epic))
                .build();

        // when
        SprintEntity sprintEntity = SprintMapper.toEntity(sprint);

        // then
        assertThat(sprintEntity).isNotNull();
        assertThat(sprintEntity.getId()).isEqualTo(sprint.getSprintId());
        assertThat(sprintEntity.getTitle()).isEqualTo(sprint.getTitle());
        assertThat(sprintEntity.getDescription()).isEqualTo(sprint.getDescription());
        assertThat(sprintEntity.getProjectId()).isEqualTo(sprint.getProjectId());
        assertThat(sprintEntity.getStartDate()).isEqualTo(sprint.getStartDate());
        assertThat(sprintEntity.getEndDate()).isEqualTo(sprint.getEndDate());
    }

    @Test
    @DisplayName("null 스프린트 변환 테스트")
    void nullSprintTest() {
        // then
        assertThatThrownBy(() -> SprintMapper.toDomain(null))
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> SprintMapper.toEntity(null))
                .isInstanceOf(NullPointerException.class);
    }
}