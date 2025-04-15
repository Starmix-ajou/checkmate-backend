package com.starmix.checkmate.domain.task;

import com.starmix.checkmate.domain.Base;
import com.starmix.checkmate.domain.Epic;
import com.starmix.checkmate.domain.user.User;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@Getter
public class Task extends Base {
    private final String name;
    private final String description;
    private final Status status;
    private final User assignee;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Priority priority;
    private final Epic epic;
}