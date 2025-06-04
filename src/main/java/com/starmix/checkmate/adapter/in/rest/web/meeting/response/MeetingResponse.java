package com.starmix.checkmate.adapter.in.rest.web.meeting.response;

import com.starmix.checkmate.adapter.in.rest.common.UserDto;
import com.starmix.checkmate.domain.meeting.Meeting;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MeetingResponse(
        String meetingId,
        String title,
        UserDto master,
        String projectId,
        LocalDate timestamp,
        String summary
) {
    public static MeetingResponse from(Meeting meeting) {
        return MeetingResponse.builder()
                .meetingId(meeting.getMeetingId())
                .title(meeting.getTitle())
                .master(UserDto.fromDomain(meeting.getMaster(), meeting.getProjectId()))
                .projectId(meeting.getProjectId())
                .timestamp(meeting.getTimestamp())
                .summary(meeting.getSummary())
                .build();
    }
}