package com.starmix.checkmate.domain.task;

import com.starmix.checkmate.domain.epic.Epic;
import com.starmix.checkmate.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Builder(toBuilder = true)
@Getter
public class Task {
    private String taskId;
    private String title;
    private String description;
    private Status status;
    private User assignee;
    private LocalDate startDate;
    private LocalDate endDate;
    private Priority priority;
    private Epic epic;

    public void updateStatus(Status status) {
        this.status = status;
    }

    public boolean isScheduledForDate(LocalDate date) {
        return !date.isBefore(this.startDate) && !date.isAfter(this.endDate);
    }

    public static Task init(
            String title, String description, User assignee,
            LocalDate startDate, LocalDate endDate, Priority priority, Epic epic
    ) {
        return Task.builder()
                .title(title)
                .description(description)
                .status(Status.TODO)
                .assignee(assignee)
                .startDate(startDate)
                .endDate(endDate)
                .priority(priority)
                .epic(epic)
                .build();
    }

    public static Task create(
            String title, String description, Status status, User assignee,
            LocalDate startDate, LocalDate endDate, Priority priority, Epic epic
    ) {
        return Task.builder()
                .title(title)
                .description(description)
                .status(status)
                .assignee(assignee)
                .startDate(startDate)
                .endDate(endDate)
                .priority(priority)
                .epic(epic)
                .build();
    }

    public static Map<Status, List<Task>> groupByStatus(List<Task> tasks) {
        return tasks.stream()
                .collect(Collectors.groupingBy(Task::getStatus));
    }

    public static List<Task> filterByStatus(List<Task> tasks, Status status) {
        return tasks.stream()
                .filter(task -> task.getStatus().equals(status))
                .toList();
    }

    public static List<Task> filterByDate(List<Task> tasks, LocalDate date) {
        return tasks.stream()
                .filter(task -> task.isScheduledForDate(date))
                .toList();
    }
}
