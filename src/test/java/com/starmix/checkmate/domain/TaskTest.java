package com.starmix.checkmate.domain;

import com.starmix.checkmate.domain.task.Review;
import com.starmix.checkmate.domain.task.Status;
import com.starmix.checkmate.domain.task.Task;
import com.starmix.checkmate.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class TaskTest {
    private Task task;
    private User assignee;

    @BeforeEach
    void setUp() {
        assignee = User.builder()
                .userId("assignee-id")
                .email("assignee@test.com")
                .name("담당자")
                .build();

        task = Task.builder()
                .taskId("task-id")
                .title("테스트 태스크")
                .description("태스크 설명")
                .assignee(assignee)
                .status(Status.TODO)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(7))
                .review(Review.builder()
                        .next("next-review")
                        .learn("learn-review")
                        .hardest("hardest-review")
                        .build()
                )
                .build();
    }

    @Test
    @DisplayName("태스크 생성 테스트")
    void createTask() {
        // when
        Task newTask = Task.builder()
                .title("새로운 태스크")
                .description("설명")
                .assignee(assignee)
                .status(Status.TODO)
                .build();

        // then
        assertThat(newTask.getTitle()).isEqualTo("새로운 태스크");
        assertThat(newTask.getStatus()).isEqualTo(Status.TODO);
        assertThat(newTask.getAssignee()).isEqualTo(assignee);
    }

    @Test
    @DisplayName("태스크 상태 변경 테스트")
    void updateStatus() {
        // when
        task.updateStatus(Status.IN_PROGRESS);

        // then
        assertThat(task.getStatus()).isEqualTo(Status.IN_PROGRESS);
    }

    @Test
    @DisplayName("담당자 변경 테스트")
    void updateAssignee() {
        // given
        User newAssignee = User.builder()
                .userId("new-assignee")
                .email("new@test.com")
                .name("새로운 담당자")
                .build();

        // when
        task.update(
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                newAssignee,
                task.getStartDate(),
                task.getEndDate(),
                task.getReview(),
                task.getPriority(),
                task.getEpic()
        );

        // then
        assertThat(task.getAssignee()).isEqualTo(newAssignee);
    }

    @Test
    @DisplayName("회고 추가 테스트")
    void updateReview() {
        // given
        Review newReview = Review.builder()
                .next("new-next-review")
                .learn("new-learn-review")
                .hardest("new-hardest-review")
                .build();

        // when
        task.update(
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getAssignee(),
                task.getStartDate(),
                task.getEndDate(),
                newReview,
                task.getPriority(),
                task.getEpic()
        );

        // then
        assertThat(task.getReview()).isEqualTo(newReview);
    }

    @Test
    @DisplayName("태스크 기간 업데이트 테스트")
    void updateDates() {
        // given
        LocalDate newStartDate = LocalDate.now().plusDays(1);
        LocalDate newEndDate = LocalDate.now().plusDays(8);

        // when
        task.update(
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getAssignee(),
                newStartDate,
                newEndDate,
                task.getReview(),
                task.getPriority(),
                task.getEpic()
        );

        // then
        assertThat(task.getStartDate()).isEqualTo(newStartDate);
        assertThat(task.getEndDate()).isEqualTo(newEndDate);
    }

    @Test
    @DisplayName("태스크 정보 업데이트 테스트")
    void updateTask() {
        // given
        String newTitle = "수정된 태스크";
        String newDescription = "수정된 설명";

        // when
        task.update(
                newTitle,
                newDescription,
                task.getStatus(),
                task.getAssignee(),
                task.getStartDate(),
                task.getEndDate(),
                task.getReview(),
                task.getPriority(),
                task.getEpic()
        );

        // then
        assertThat(task.getTitle()).isEqualTo(newTitle);
        assertThat(task.getDescription()).isEqualTo(newDescription);
    }

    @Test
    @DisplayName("태스크 완료 여부 확인 테스트")
    void isCompleted() {
        // when
        task.updateStatus(Status.DONE);

        // then
        assertThat(task.getStatus().equals(Status.DONE)).isTrue();
    }
}