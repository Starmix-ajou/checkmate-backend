package com.starmix.checkmate.domain.meeting;

import com.starmix.checkmate.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
public class Meeting {
    private String meetingId;
    private String title;
    private String content;
    private List<User> participants;
    private User master;
    private String projectId;
    private LocalDate timestamp;

    public static Meeting create(User creator, String projectId) {
        String meetingId = UUID.randomUUID().toString();

        return Meeting.builder()
                .meetingId(meetingId)
                .participants(List.of(creator))
                .master(creator)
                .projectId(projectId)
                .timestamp(LocalDate.now())
                .build();
    }

    public void update(
            String title, String content,
            User master, List<User> participants
    ) {
        this.title = title;
        this.content = content;
        this.participants = participants;
        this.master = master;
    }
}