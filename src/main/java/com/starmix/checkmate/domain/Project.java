package com.starmix.checkmate.domain;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Getter
@SuperBuilder
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