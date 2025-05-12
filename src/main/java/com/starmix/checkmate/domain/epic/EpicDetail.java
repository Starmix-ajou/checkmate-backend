package com.starmix.checkmate.domain.epic;

import com.starmix.checkmate.domain.task.Task;
import lombok.Builder;

import java.util.List;

@Builder
public record EpicDetail(
        Epic epic,
        List<Task> tasks
) { }
