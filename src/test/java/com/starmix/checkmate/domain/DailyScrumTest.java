package com.starmix.checkmate.domain;

import com.starmix.checkmate.domain.dailyScrum.DailyScrum;
import com.starmix.checkmate.domain.task.Status;
import com.starmix.checkmate.domain.task.Task;
import com.starmix.checkmate.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DailyScrumTest {
    private DailyScrum dailyScrum;
    private User user;
    private List<Task> todoTasks;
    private List<Task> doneTasks;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .userId("user-id")
                .email("user@test.com")
                .name("사용자")
                .build();

        todoTasks = new ArrayList<>();
        doneTasks = new ArrayList<>();

        dailyScrum = DailyScrum.builder()
                .dailyScrumId("dailyscrum-id")
                .projectId("project-id")
                .timestamp(LocalDate.now())
                .todoTasks(new ArrayList<>())
                .doneTasks(new ArrayList<>())
                .build();
    }

    @Test
    @DisplayName("데일리 스크럼 생성 테스트")
    void createDailyScrum() {
        // when
        DailyScrum newDailyScrum = DailyScrum.create("project-id");

        // then
        assertThat(newDailyScrum.getProjectId()).isEqualTo("project-id");
        assertThat(newDailyScrum.getTimestamp()).isEqualTo(LocalDate.now());
        assertThat(newDailyScrum.getTodoTasks()).isEmpty();
        assertThat(newDailyScrum.getDoneTasks()).isEmpty();
    }

    @Test
    @DisplayName("태스크 업데이트 테스트")
    void updateTasks() {
        // given
        Task todoTask = Task.builder()
                .taskId("todo-task")
                .assignee(user)
                .status(Status.TODO)
                .build();

        Task doneTask = Task.builder()
                .taskId("done-task")
                .assignee(user)
                .status(Status.IN_PROGRESS)
                .build();

        todoTasks.add(todoTask);
        doneTasks.add(doneTask);

        // when
        dailyScrum.updateTasks(todoTasks, doneTasks, user);

        // then
        assertThat(dailyScrum.getTodoTasks())
                .hasSize(1)
                .contains(todoTask);
        assertThat(dailyScrum.getDoneTasks())
                .hasSize(1)
                .contains(doneTask);

        assertThat(todoTask.getStatus()).isEqualTo(Status.IN_PROGRESS);
        assertThat(doneTask.getStatus()).isEqualTo(Status.DONE);
    }

    @Test
    @DisplayName("다른 사용자의 태스크는 유지되어야 함")
    void maintainOtherUserTasks() {
        // given
        User otherUser = User.builder()
                .userId("other-user")
                .build();

        Task otherUserTask = Task.builder()
                .taskId("other-task")
                .assignee(otherUser)
                .build();

        dailyScrum.getTodoTasks().add(otherUserTask);

        // when
        dailyScrum.updateTasks(new ArrayList<>(), new ArrayList<>(), user);

        // then
        assertThat(dailyScrum.getTodoTasks())
                .hasSize(1)
                .contains(otherUserTask);
    }
}