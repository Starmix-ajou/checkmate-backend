package com.starmix.checkmate.adapter.in.sse.sprint.request;

import java.util.List;

public record CreateSprintRequest(
        List<String> pendingTaskIds
) { }