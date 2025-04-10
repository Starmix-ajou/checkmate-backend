package com.starmix.checkmate.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class Project extends Base {
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> frameworks;
    private List<String> userIds;
    private String leaderId;
    private List<String> sprintIds;
}