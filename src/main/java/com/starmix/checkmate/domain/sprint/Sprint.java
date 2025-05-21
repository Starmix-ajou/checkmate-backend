package com.starmix.checkmate.domain.sprint;

import com.starmix.checkmate.domain.epic.Epic;
import com.starmix.checkmate.global.exception.CustomException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    @Builder.Default
    private List<Epic> epics = new ArrayList<>();

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

    public void addEpic(Epic epic) {
        if (this.epics.contains(epic)) {
            throw new CustomException("Epic Already Exists", HttpStatus.BAD_REQUEST);
        }
        List<Epic> existingEpics = new ArrayList<>(this.epics);
        existingEpics.add(epic);
        this.epics = existingEpics;
    }
}