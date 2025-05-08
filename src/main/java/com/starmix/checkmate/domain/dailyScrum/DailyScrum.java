package com.starmix.checkmate.domain.dailyScrum;

import com.starmix.checkmate.domain.task.Task;
import com.starmix.checkmate.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Builder(toBuilder = true)
@Getter
public class DailyScrum {
    private String dailyScrumId;
    private LocalDate timestamp;
    private List<Task> todoTasks;
    private List<Task> doneTasks;
    private String projectId;

    public void updateTasks(List<Task> todoTasks, List<Task> doneTasks, User user) {
        this.todoTasks = this.todoTasks.stream()
                .filter(task -> !Objects.equals(task.getAssignee(), user))
                .collect(Collectors.toCollection(ArrayList::new));

        this.doneTasks = this.doneTasks.stream()
                .filter(task -> !Objects.equals(task.getAssignee(), user))
                .collect(Collectors.toCollection(ArrayList::new));

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