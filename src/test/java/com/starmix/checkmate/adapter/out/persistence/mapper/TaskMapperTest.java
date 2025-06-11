package com.starmix.checkmate.adapter.out.persistence.mapper;

import com.starmix.checkmate.adapter.out.persistence.entity.EpicEntity;
import com.starmix.checkmate.adapter.out.persistence.entity.TaskEntity;
import com.starmix.checkmate.adapter.out.persistence.entity.UserEntity;
import com.starmix.checkmate.domain.epic.Epic;
import com.starmix.checkmate.domain.task.Priority;
import com.starmix.checkmate.domain.task.Review;
import com.starmix.checkmate.domain.task.Status;
import com.starmix.checkmate.domain.task.Task;
import com.starmix.checkmate.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TaskMapperTest {

    @Test
    @DisplayName("TaskEntity -> Task 도메인 변환 테스트")
    void toDomainTest() {
        // given
        LocalDate today = LocalDate.now();

        UserEntity assigneeEntity = UserEntity.builder()
                .id("user-123")
                .name("홍길동")
                .email("hong@example.com")
                .build();

        EpicEntity epicEntity = EpicEntity.builder()
                .id("epic-123")
                .title("테스트 에픽")
                .build();

        TaskEntity taskEntity = TaskEntity.builder()
                .id("task-123")
                .title("테스트 태스크")
                .description("태스크 설명")
                .status(Status.IN_PROGRESS)
                .assignee(assigneeEntity)
                .startDate(today)
                .endDate(today.plusDays(7))
                .priority(Priority.HIGH.getPriorityNum())
                .epic(epicEntity)
                .doneDate(null)
                .build();

        Task task = TaskMapper.toDomain(taskEntity);

        // then
        assertThat(task).isNotNull();
        assertThat(task.getTaskId()).isEqualTo(taskEntity.getId());
        assertThat(task.getTitle()).isEqualTo(taskEntity.getTitle());
        assertThat(task.getDescription()).isEqualTo(taskEntity.getDescription());
        assertThat(task.getStatus()).isEqualTo(Status.IN_PROGRESS);
        assertThat(task.getStartDate()).isEqualTo(taskEntity.getStartDate());
        assertThat(task.getEndDate()).isEqualTo(taskEntity.getEndDate());
        assertThat(task.getPriority()).isEqualTo(Priority.HIGH);
        assertThat(task.getDoneDate()).isNull();
    }

    @Test
    @DisplayName("Task 도메인 -> TaskEntity 변환 테스트")
    void toEntityTest() {
        // given
        LocalDate today = LocalDate.now();

        User assignee = User.builder()
                .userId("user-456")
                .name("김철수")
                .email("kim@example.com")
                .build();

        Epic epic = Epic.builder()
                .epicId("epic-456")
                .title("새 에픽")
                .build();

        Review review = new Review(
                "좋은 작업입니다.",
                "좋은 작업입니다.",
                "좋은 작업입니다."
        );

        Task task = Task.builder()
                .taskId("task-456")
                .title("새 태스크")
                .description("새 태스크 설명")
                .status(Status.DONE)
                .assignee(assignee)
                .startDate(today.minusDays(5))
                .endDate(today.plusDays(10))
                .priority(Priority.MEDIUM)
                .epic(epic)
                .review(review)
                .doneDate(today)
                .build();

        TaskEntity taskEntity = TaskMapper.toEntity(task);

        // then
        assertThat(taskEntity).isNotNull();
        assertThat(taskEntity.getId()).isEqualTo(task.getTaskId());
        assertThat(taskEntity.getTitle()).isEqualTo(task.getTitle());
        assertThat(taskEntity.getDescription()).isEqualTo(task.getDescription());
        assertThat(taskEntity.getStatus()).isEqualTo(task.getStatus());
        assertThat(taskEntity.getStartDate()).isEqualTo(task.getStartDate());
        assertThat(taskEntity.getEndDate()).isEqualTo(task.getEndDate());
        assertThat(taskEntity.getPriority()).isEqualTo(task.getPriority().getPriorityNum());
        assertThat(taskEntity.getDoneDate()).isEqualTo(task.getDoneDate());
        assertThat(taskEntity.getReview()).isEqualTo(task.getReview());
    }

    @Test
    @DisplayName("Done 상태 태스크 변환 테스트")
    void doneTaskTest() {
        // given
        LocalDate today = LocalDate.now();

        UserEntity assigneeEntity = UserEntity.builder()
                .id("user-123")
                .name("홍길동")
                .email("hong@example.com")
                .build();

        EpicEntity epicEntity = EpicEntity.builder()
                .id("epic-123")
                .title("테스트 에픽")
                .build();

        TaskEntity doneEntity = TaskEntity.builder()
                .id("task-done")
                .title("완료된 태스크")
                .description("태스크 설명")
                .status(Status.DONE)
                .assignee(assigneeEntity)
                .startDate(today)
                .endDate(today.plusDays(7))
                .priority(Priority.HIGH.getPriorityNum())
                .epic(epicEntity)
                .doneDate(today)
                .build();

        // when
        Task doneTask = TaskMapper.toDomain(doneEntity);

        // then
        assertThat(doneTask).isNotNull();
        assertThat(doneTask.getStatus()).isEqualTo(Status.DONE);
        assertThat(doneTask.getDoneDate()).isEqualTo(today);
    }

    @Test
    @DisplayName("Review가 있는 태스크 변환 테스트")
    void reviewTaskTest() {
        // given
        LocalDate today = LocalDate.now();

        User assignee = User.builder()
                .userId("user-456")
                .name("김철수")
                .email("kim@example.com")
                .build();

        Epic epic = Epic.builder()
                .epicId("epic-456")
                .title("새 에픽")
                .build();

        Review review = new Review(
                "좋은 작업입니다.",
                "좋은 작업입니다.",
                "좋은 작업입니다."
        );

        Task taskWithReview = Task.builder()
                .taskId("task-review")
                .title("리뷰 있는 태스크")
                .description("새 태스크 설명")
                .status(Status.DONE)
                .assignee(assignee)
                .startDate(today.minusDays(5))
                .endDate(today.plusDays(10))
                .priority(Priority.MEDIUM)
                .epic(epic)
                .review(review)
                .doneDate(today)
                .build();

        // when
        TaskEntity entity = TaskMapper.toEntity(taskWithReview);

        // then
        assertThat(entity).isNotNull();
        assertThat(entity.getReview()).isEqualTo(review);
    }

    @Test
    @DisplayName("null 태스크 변환 테스트")
    void nullTaskTest() {
        // then
        assertThatThrownBy(() -> TaskMapper.toDomain(null))
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> TaskMapper.toEntity(null))
                .isInstanceOf(NullPointerException.class);
    }
}