package com.starmix.checkmate.adapter.out.ai.client.request;

import com.starmix.checkmate.domain.meeting.Meeting;
import lombok.Builder;

@Builder
public record CreateMeetingFeignRequest(
        String title,
        String content,
        String projectId
) {
    public static CreateMeetingFeignRequest fromDomain(Meeting meeting) {
        return CreateMeetingFeignRequest.builder()
                .title(meeting.getTitle())
                .content(meeting.getContent())
                .projectId(meeting.getProjectId())
                .build();
    }
}