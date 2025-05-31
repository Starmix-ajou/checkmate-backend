package com.starmix.checkmate.adapter.in.rest.web.common;

import com.starmix.checkmate.domain.epic.Epic;
import com.starmix.checkmate.domain.sprint.Sprint;
import lombok.Builder;

@Builder
public record EpicDto(
        String epicId,
        String title,
        String description,
        String projectId,
        Sprint sprint
) {
    public static EpicDto fromDomain(Epic epic, Sprint sprint) {
        return EpicDto.builder()
                .epicId(epic.getEpicId())
                .title(epic.getTitle())
                .description(epic.getDescription())
                .projectId(epic.getProjectId())
                .sprint(sprint)
                .build();
    }
}
