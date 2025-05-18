package com.starmix.checkmate.adapter.in.http.meeting.request;

import java.util.List;

public record UpdateMeetingRequest(
    String title,
    String content,
    List<String> participantIds,
    String masterId
) { }
