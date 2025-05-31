package com.starmix.checkmate.adapter.in.sse.web.sprint.request;

import java.util.List;

public record CreateSprintRequest(
        List<String> pendingTaskIds
) { }