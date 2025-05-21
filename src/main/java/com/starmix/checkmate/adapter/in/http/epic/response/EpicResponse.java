package com.starmix.checkmate.adapter.in.http.epic.response;

import com.starmix.checkmate.domain.epic.Epic;
import com.starmix.checkmate.domain.sprint.Sprint;
import com.starmix.checkmate.domain.task.Task;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record EpicResponse(
        String epicId,
        String title,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        Sprint sprint,
        List<Task> tasks
) {
    public static EpicResponse fromDomain(Epic epic, List<Task> tasks, Sprint sprint) {
        return EpicResponse.builder()
                .epicId(epic.getEpicId())
                .title(epic.getTitle())
                .description(epic.getDescription())
                .startDate(epic.getStartDate())
                .endDate(epic.getEndDate())
                .tasks(tasks)
                .sprint(sprint)
                .build();
    }
}