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
    private Integer priority;
    private Epic epic;
}