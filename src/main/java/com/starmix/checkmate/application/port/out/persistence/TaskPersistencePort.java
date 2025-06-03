package com.starmix.checkmate.application.port.out.persistence;

import com.starmix.checkmate.adapter.out.persistence.dto.TaskCountPersistenceDto;
import com.starmix.checkmate.domain.task.Priority;
import com.starmix.checkmate.domain.task.Status;
import com.starmix.checkmate.domain.task.Task;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskPersistencePort {
    Optional<Task> findById(String id);
    String save(Task task);
    void delete(String id);
    List<Task> filterTasks(
            String projectId, List<String> epicId, List<String> sprintId,
            List<String> assigneeEmail, List<Priority> priority,
            LocalDate startDate, LocalDate endDate, List<Status> status
    );
    List<Task> findByAssigneeId(String projectId, String assigneeId);
    List<Task> findMyTasksByStartDateAndEndDate(String projectId, String assigneeId, LocalDate startDate, LocalDate endDate);
    List<Task> findAllByEpicId(String epicId);
    TaskCountPersistenceDto countByStartDateAndEndDate(LocalDate startDate, LocalDate endDate);
    Integer countReviewedBySprintId(String sprintId);
}
