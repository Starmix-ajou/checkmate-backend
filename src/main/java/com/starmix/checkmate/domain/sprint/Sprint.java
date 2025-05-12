package com.starmix.checkmate.domain.sprint;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

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

    public static Sprint create(
            String title, String description, Integer sequence,
            String projectId, LocalDate startDate, LocalDate endDate
    ) {
        String sprintId = UUID.randomUUID().toString();

        return Sprint.builder()
                .sprintId(sprintId)
                .title(title)
                .description(description)
                .sequence(sequence)
                .projectId(projectId)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}