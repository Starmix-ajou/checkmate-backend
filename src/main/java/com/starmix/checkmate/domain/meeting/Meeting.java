package com.starmix.checkmate.domain.meeting;

import com.starmix.checkmate.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(toBuilder = true)
public class Meeting {
    private String meetingId;
    private String title;
    private String content;
    private List<User> participants;
    private User master;
    private String projectId;
}