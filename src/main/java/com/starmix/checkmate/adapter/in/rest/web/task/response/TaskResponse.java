package com.starmix.checkmate.adapter.in.rest.web.task.response;

import com.starmix.checkmate.adapter.in.rest.common.UserDto;
import com.starmix.checkmate.domain.epic.Epic;
import com.starmix.checkmate.domain.sprint.Sprint;
import com.starmix.checkmate.domain.task.Priority;
import com.starmix.checkmate.domain.task.Status;
import com.starmix.checkmate.domain.task.Task;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TaskResponse(
        String taskId,
        String title,
        String description,
        Status status,
        UserDto assignee,
        LocalDate startDate,
        LocalDate endDate,
        Priority priority,
        EpicDto epic,
        String review
) {
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

    public static TaskResponse fromDomain(Task task, Sprint sprint) {
        return TaskResponse.builder()
                .taskId(task.getTaskId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .assignee(UserDto.fromDomain(task.getAssignee(), sprint.getProjectId()))
                .startDate(task.getStartDate())
                .endDate(task.getEndDate())
                .priority(task.getPriority())
                .epic(EpicDto.fromDomain(task.getEpic(), sprint))
                .review(task.getReview())
                .build();
    }
}
