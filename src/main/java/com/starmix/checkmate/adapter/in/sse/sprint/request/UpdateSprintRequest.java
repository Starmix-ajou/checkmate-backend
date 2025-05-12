package com.starmix.checkmate.adapter.in.sse.sprint.request;

import com.starmix.checkmate.domain.task.Task;

import java.util.List;

public record UpdateSprintRequest(
        String epicId,
        List<Task> tasks
) { }