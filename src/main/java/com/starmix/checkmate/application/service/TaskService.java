package com.starmix.checkmate.application.service;

import com.starmix.checkmate.application.port.out.persistence.TaskPersistencePort;
import com.starmix.checkmate.domain.task.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskPersistencePort taskPersistencePort;

    public List<Task> getTasks() {
        return taskPersistencePort.findAll();
    }

    public Task getTask(String taskId) {
        Optional<Task> task = taskPersistencePort.findById(taskId);
        return task.orElse(null);
    }
}