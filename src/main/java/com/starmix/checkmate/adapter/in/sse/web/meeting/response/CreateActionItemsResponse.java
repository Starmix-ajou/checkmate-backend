package com.starmix.checkmate.adapter.in.sse.web.meeting.response;

import com.starmix.checkmate.adapter.in.rest.web.task.response.TaskResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record CreateActionItemsResponse(
        List<TaskResponse> tasks
) { }