package com.starmix.checkmate.application.port.out.persistence;

import com.starmix.checkmate.domain.task.Priority;
import com.starmix.checkmate.domain.task.Task;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskPersistencePort {
    List<Task> findAll();
    Optional<Task> findById(String id);
    String save(Task task);
    String update(String id, Task task);
    void delete(String id);
    List<Task> filterTasks(
            String projectId, String epicId, String sprintId,
            String assigneeEmail, Priority priority,
            LocalDate startDate, LocalDate endDate
    );
}
