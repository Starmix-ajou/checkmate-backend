package com.starmix.checkmate.domain.task;

import com.starmix.checkmate.domain.epic.Epic;
import com.starmix.checkmate.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

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
}