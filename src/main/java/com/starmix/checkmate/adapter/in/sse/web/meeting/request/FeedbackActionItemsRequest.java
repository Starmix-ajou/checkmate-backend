package com.starmix.checkmate.adapter.in.sse.web.meeting.request;

import com.starmix.checkmate.adapter.in.sse.web.meeting.common.ActionItemDto;

import java.util.List;

public record FeedbackActionItemsRequest(
        List<ActionItemDto> tasks
) { }