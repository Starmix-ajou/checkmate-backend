package com.starmix.checkmate.adapter.in.sse.web.meeting.request;

import java.util.List;

public record CreateActionItemsRequest(
        List<String> actionItems
) { }