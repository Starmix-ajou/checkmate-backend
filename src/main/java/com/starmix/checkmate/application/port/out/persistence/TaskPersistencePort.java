package com.starmix.checkmate.application.port.out.persistence;

import com.starmix.checkmate.domain.task.Task;

import java.util.List;
import java.util.Optional;

public interface TaskPersistencePort {
    List<Task> findAll();
    Optional<Task> findById(String id);
}
