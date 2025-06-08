package com.starmix.checkmate.domain.task;

import com.starmix.checkmate.domain.epic.Epic;
import com.starmix.checkmate.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

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
    private Review review;
    private LocalDate doneDate;

    public void updateStatus(Status status) {
        this.status = status;
        if(status == Status.DONE) {
            this.doneDate = LocalDate.now();
        }
    }

    public boolean isScheduledForDate(LocalDate date) {
        return !date.isBefore(this.startDate) && !date.isAfter(this.endDate);
    }

    public static Task init(
            String title, String description, User assignee,
            LocalDate endDate, Epic epic
    ) {
        return Task.builder()
                .title(title)
                .description(description)
                .status(Status.TODO)
                .assignee(assignee)
                .startDate(LocalDate.now())
                .endDate(endDate)
                .priority(Priority.HIGH)
                .epic(epic)
                .build();
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
                .doneDate(status == Status.DONE ? LocalDate.now() : null)
                .build();
    }

    public void update(
            String title, String description, Status status, User assignee,
            LocalDate startDate, LocalDate endDate,
            Review review, Priority priority, Epic epic
    ) {
        if(status == Status.DONE) {
            this.doneDate = LocalDate.now();
        }

        this.title = title;
        this.description = description;
        this.status = status;
        this.assignee = assignee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priority = priority;
        this.epic = epic;
        this.review = review;
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
