package com.starmix.checkmate.adapter.in.sse.web.meeting.response;

import lombok.Builder;

import java.util.List;

@Builder
public record SaveMeetingResponse(
        String summary,
        List<String> actionItems
) { }