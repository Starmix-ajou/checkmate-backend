package com.starmix.checkmate.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class DailyScrum extends Base {
    private final LocalDateTime timestamp;
    private final List<String> todoTaskIds;
    private final List<String> doneTaskIds;
    private final List<Long> projectIds;
}