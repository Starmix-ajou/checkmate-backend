package com.starmix.checkmate.domain.dailyScrum;

import com.starmix.checkmate.domain.task.Task;
import com.starmix.checkmate.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder(toBuilder = true)
@Getter
public class DailyScrum {
    private String dailyScrumId;
    private LocalDate timestamp;
    private List<Task> todoTasks;
    private List<Task> doneTasks;
    private String projectId;

    public void updateTasks(List<Task> todoTasks, List<Task> doneTasks, User user) {
        List<Task> filteredTodoTasks = this.todoTasks.stream()
                .filter(task -> !task.getAssignee().equals(user))
                .toList();

        List<Task> filteredDoneTasks = this.doneTasks.stream()
                .filter(task -> !task.getAssignee().equals(user))
                .toList();

        this.todoTasks = new ArrayList<>(filteredTodoTasks);
        this.doneTasks = new ArrayList<>(filteredDoneTasks);

        this.todoTasks.addAll(todoTasks);
        this.doneTasks.addAll(doneTasks);
    }

    public static DailyScrum create(String projectId) {
        return DailyScrum.builder()
                .timestamp(LocalDate.now())
                .todoTasks(new ArrayList<>())
                .doneTasks(new ArrayList<>())
                .projectId(projectId)
                .build();
    }
}