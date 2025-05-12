package com.starmix.checkmate.adapter.in.sse.sprint.response;

import com.starmix.checkmate.domain.epic.Epic;
import com.starmix.checkmate.domain.task.Task;
import lombok.Builder;

import java.util.List;

@Builder
public record CreateSprintResponse(
        Epic epic,
        List<Task> features
) { }