package com.starmix.checkmate.domain.dailyScrum;

import com.starmix.checkmate.domain.Base;
import com.starmix.checkmate.domain.task.Task;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@SuperBuilder
@Getter
public class DailyScrum extends Base {
    private final LocalDate timestamp;
    private final List<Task> todoTasks;
    private final List<Task> doneTasks;
    private final String projectId;
}