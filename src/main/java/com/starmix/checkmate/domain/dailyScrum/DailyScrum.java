package com.starmix.checkmate.domain.dailyScrum;

import com.starmix.checkmate.domain.task.Task;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder(toBuilder = true)
@Getter
public class DailyScrum {
    private String dailyScrumId;
    private LocalDate timestamp;
    private List<Task> todoTasks;
    private List<Task> doneTasks;
    private String projectId;
}