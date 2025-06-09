package com.starmix.checkmate.domain;

import com.starmix.checkmate.domain.epic.Epic;
import com.starmix.checkmate.domain.sprint.Sprint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class SprintTest {
    private Sprint sprint;
    private String projectId;
    private LocalDate startDate;
    private LocalDate endDate;

    @BeforeEach
    void setUp() {
        projectId = "project-id";
        startDate = LocalDate.now();
        endDate = startDate.plusDays(14);

        sprint = Sprint.builder()
                .sprintId("sprint-id")
                .title("테스트 스프린트")
                .description("스프린트 설명")
                .sequence(1)
                .projectId(projectId)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }

    @Test
    @DisplayName("스프린트 생성 테스트")
    void createSprint() {
        // given
        String title = "새로운 스프린트";
        String description = "새로운 스프린트 설명";
        Integer sequence = 2;

        // when
        Sprint newSprint = Sprint.create(
                title,
                description,
                sequence,
                projectId,
                startDate,
                endDate
        );

        // then
        assertThat(newSprint.getSprintId()).isNotNull();
        assertThat(newSprint.getTitle()).isEqualTo(title);
        assertThat(newSprint.getDescription()).isEqualTo(description);
        assertThat(newSprint.getSequence()).isEqualTo(sequence);
        assertThat(newSprint.getProjectId()).isEqualTo(projectId);
        assertThat(newSprint.getStartDate()).isEqualTo(startDate);
        assertThat(newSprint.getEndDate()).isEqualTo(endDate);
        assertThat(newSprint.getEpics()).isNull();
    }

    @Test
    @DisplayName("스프린트에 에픽 추가 테스트")
    void addEpic() {
        // given
        Epic epic = Epic.builder()
                .epicId("epic-id")
                .title("테스트 에픽")
                .build();

        // when
        sprint.addEpic(epic);

        // then
        assertThat(sprint.getEpics())
                .isNotNull()
                .hasSize(1)
                .contains(epic);
    }

    @Test
    @DisplayName("스프린트에 여러 에픽 추가 테스트")
    void addMultipleEpics() {
        // given
        Epic epic1 = Epic.builder()
                .epicId("epic-1")
                .title("첫 번째 에픽")
                .build();

        Epic epic2 = Epic.builder()
                .epicId("epic-2")
                .title("두 번째 에픽")
                .build();

        // when
        sprint.addEpic(epic1);
        sprint.addEpic(epic2);

        // then
        assertThat(sprint.getEpics())
                .isNotNull()
                .hasSize(2)
                .containsExactly(epic1, epic2);
    }

    @Test
    @DisplayName("에픽 리스트가 null일 때 에픽 추가 테스트")
    void addEpicWhenListIsNull() {
        // given
        Epic epic = Epic.builder()
                .epicId("epic-id")
                .title("테스트 에픽")
                .build();
        
        assertThat(sprint.getEpics()).isNull();

        // when
        sprint.addEpic(epic);

        // then
        assertThat(sprint.getEpics())
                .isNotNull()
                .hasSize(1)
                .contains(epic);
    }
}