package com.starmix.checkmate.domain.task;

import com.starmix.checkmate.domain.Base;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
public class Task extends Base {
    private final String name;
    private final String description;
    private final String status;
    private final String assigneeId;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String priority;
    private final List<String> commentIds;
}