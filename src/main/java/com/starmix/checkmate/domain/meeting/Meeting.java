package com.starmix.checkmate.domain.meeting;

import com.starmix.checkmate.adapter.in.sse.web.meeting.request.SaveMeetingRequest;
import com.starmix.checkmate.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
public class Meeting {
    private String meetingId;
    private String title;
    private String content;
    private User master;
    private String projectId;
    private LocalDate timestamp;
    private String summary;

    public static Meeting create(User creator, String projectId) {
        String meetingId = UUID.randomUUID().toString();
        LocalDate today = LocalDate.now();
        String title = today.format(DateTimeFormatter.ofPattern("MM월 dd일")) + "의 회의록";

        return Meeting.builder()
                .meetingId(meetingId)
                .title(title)
                .master(creator)
                .projectId(projectId)
                .timestamp(today)
                .build();
    }

    public void save(SaveMeetingRequest request, User master, String summary) {
        this.meetingId = request.meetingId();
        this.title = request.title();
        this.content = request.content();
        this.master = master;
        this.summary = summary;
    }

    public void addContent(String content) {
        this.content = content;
    }
}