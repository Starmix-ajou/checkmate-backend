package com.starmix.checkmate.adapter.in.sse.web.meeting.request;

public record SaveMeetingRequest(
        String meetingId,
        String title,
        String content,
        String masterId
) { }