package com.starmix.checkmate.adapter.in.sse.web.meeting.common;

import com.starmix.checkmate.domain.task.Priority;
import com.starmix.checkmate.domain.task.Task;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ActionItemDto(
        String title,
        String description,
        String assigneeId,
        LocalDate endDate,
        String epicId,
        Priority priority
) {
    public static ActionItemDto fromDomain(Task task) {
        return ActionItemDto.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .assigneeId(task.getAssignee().getUserId())
                .endDate(task.getEndDate())
                .epicId(task.getEpic().getEpicId())
                .priority(task.getPriority())
                .build();
    }
}