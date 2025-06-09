package com.starmix.checkmate.domain;

import com.starmix.checkmate.domain.epic.Epic;
import com.starmix.checkmate.domain.feature.Feature;
import com.starmix.checkmate.domain.sprint.Sprint;
import com.starmix.checkmate.domain.task.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EpicTest {
    private Epic epic;
    private String projectId;
    private List<Task> tasks;
    private List<Sprint> sprints;

    @BeforeEach
    void setUp() {
        projectId = "project-id";
        epic = Epic.builder()
                .epicId("epic-id")
                .title("테스트 에픽")
                .description("에픽 설명")
                .projectId(projectId)
                .build();

        tasks = new ArrayList<>();
        sprints = new ArrayList<>();
    }

    @Test
    @DisplayName("에픽 생성 테스트")
    void createEpic() {
        // given
        String title = "새로운 에픽";
        String description = "새로운 설명";

        // when
        Epic newEpic = Epic.create(title, description, projectId);

        // then
        assertThat(newEpic.getEpicId()).isNotNull();
        assertThat(newEpic.getTitle()).isEqualTo(title);
        assertThat(newEpic.getDescription()).isEqualTo(description);
        assertThat(newEpic.getProjectId()).isEqualTo(projectId);
        assertThat(newEpic.getFeatureId()).isNull();
    }

    @Test
    @DisplayName("Feature로부터 Epic 생성 테스트")
    void createEpicFromFeature() {
        // given
        Feature feature = Feature.builder()
                .featureId("feature-id")
                .name("feature name")
                .useCase("feature usecase")
                .build();

        // when
        Epic epicFromFeature = Epic.fromFeature(feature, projectId);

        // then
        assertThat(epicFromFeature.getEpicId()).isNotNull();
        assertThat(epicFromFeature.getTitle()).isEqualTo(feature.getName());
        assertThat(epicFromFeature.getDescription()).isEqualTo(feature.getUseCase());
        assertThat(epicFromFeature.getProjectId()).isEqualTo(projectId);
        assertThat(epicFromFeature.getFeatureId()).isEqualTo(feature.getFeatureId());
    }

    @Test
    @DisplayName("태스크 기반 날짜 업데이트 테스트")
    void updateDates() {
        // given
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(5);

        tasks.add(Task.builder().startDate(startDate).endDate(endDate).build());
        tasks.add(Task.builder().startDate(startDate.plusDays(1)).endDate(endDate.plusDays(1)).build());

        // when
        epic.updateDates(tasks);

        // then
        assertThat(epic.getStartDate()).isEqualTo(startDate);
        assertThat(epic.getEndDate()).isEqualTo(endDate.plusDays(1));
    }

    @Test
    @DisplayName("현재 스프린트 찾기 테스트")
    void findCurrentSprint() {
        // given
        LocalDate today = LocalDate.now();
        Sprint currentSprint = Sprint.builder()
                .sprintId("sprint-1")
                .startDate(today.minusDays(1))
                .endDate(today.plusDays(6))
                .sequence(1)
                .build();

        Sprint pastSprint = Sprint.builder()
                .sprintId("sprint-2")
                .startDate(today.minusDays(14))
                .endDate(today.minusDays(7))
                .sequence(0)
                .build();

        sprints.add(currentSprint);
        sprints.add(pastSprint);

        // when
        Sprint found = epic.findCurrentSprint(sprints);

        // then
        assertThat(found).isEqualTo(currentSprint);
    }

    @Test
    @DisplayName("최신 스프린트 찾기 테스트")
    void findLatestSprint() {
        // given
        Sprint sprint1 = Sprint.builder().sequence(1).build();
        Sprint sprint2 = Sprint.builder().sequence(2).build();
        Sprint sprint3 = Sprint.builder().sequence(3).build();

        sprints.add(sprint1);
        sprints.add(sprint2);
        sprints.add(sprint3);

        // when
        Sprint latestSprint = epic.findLatestSprint(sprints);

        // then
        assertThat(latestSprint).isEqualTo(sprint3);
    }

    @Test
    @DisplayName("에픽 동등성 비교 테스트")
    void testEquals() {
        // given
        Epic sameEpic = Epic.builder()
                .epicId(epic.getEpicId())
                .title("다른 제목")
                .build();

        Epic differentEpic = Epic.builder()
                .epicId("different-id")
                .title(epic.getTitle())
                .build();

        // then
        assertThat(epic).isEqualTo(sameEpic);
        assertThat(epic).isNotEqualTo(differentEpic);
    }
}