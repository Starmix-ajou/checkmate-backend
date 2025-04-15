package com.starmix.checkmate.domain.meeting;

import com.starmix.checkmate.domain.Base;
import com.starmix.checkmate.domain.user.User;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class Meeting extends Base {
    private final String title;
    private final String content;
    private final List<User> participants;
    private final User master;
    private final String projectId;
}