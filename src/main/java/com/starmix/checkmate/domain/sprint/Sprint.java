package com.starmix.checkmate.domain.sprint;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder(toBuilder = true)
@Getter
public class Sprint {
    private String sprintId;
    private String title;
    private String description;
    private Integer sequence;
    private String projectId;
    private LocalDate startDate;
    private LocalDate endDate;
}