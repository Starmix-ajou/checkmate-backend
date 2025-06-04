package com.starmix.checkmate.adapter.in.sse.web.meeting.request;

import com.starmix.checkmate.adapter.in.rest.web.task.request.CreateTaskRequest;

import java.util.List;

public record FeedbackActionItemsRequest(
        List<CreateTaskRequest> tasks
) { }