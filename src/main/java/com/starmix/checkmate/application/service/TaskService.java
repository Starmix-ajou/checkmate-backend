package com.starmix.checkmate.application.service;

import com.starmix.checkmate.adapter.in.common.EpicDto;
import com.starmix.checkmate.adapter.in.common.TaskDto;
import com.starmix.checkmate.adapter.in.http.task.request.CreateTaskRequest;
import com.starmix.checkmate.adapter.in.http.task.request.UpdateTaskRequest;
import com.starmix.checkmate.application.port.out.persistence.EpicPersistencePort;
import com.starmix.checkmate.application.port.out.persistence.SprintPersistencePort;
import com.starmix.checkmate.application.port.out.persistence.TaskPersistencePort;
import com.starmix.checkmate.application.port.out.persistence.UserPersistencePort;
import com.starmix.checkmate.domain.epic.Epic;
import com.starmix.checkmate.domain.sprint.Sprint;
import com.starmix.checkmate.domain.task.Task;
import com.starmix.checkmate.domain.user.User;
import com.starmix.checkmate.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskService {
    private final TaskPersistencePort taskPersistencePort;
    private final UserPersistencePort userPersistencePort;
    private final EpicPersistencePort epicPersistencePort;
    private final SprintPersistencePort sprintPersistencePort;

    public List<Task> getTasks() {
        return taskPersistencePort.findAll();
    }

    public TaskDto getTask(String taskId) {
        Task task = taskPersistencePort.findById(taskId)
                .orElseThrow(() -> new CustomException("Task not found", HttpStatus.NOT_FOUND));
        Sprint sprint = sprintPersistencePort.findById(task.getEpic().getSprintId())
                .orElseThrow(() -> new CustomException("Sprint not found", HttpStatus.NOT_FOUND));
        EpicDto epicDto = EpicDto.fromDomain(task.getEpic(), sprint);
        return TaskDto.fromDomain(task, epicDto);
    }

    public void createTask(CreateTaskRequest request) {
        User assignee = userPersistencePort.findByEmail(request.assigneeEmail())
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.FORBIDDEN));
        Epic epic = epicPersistencePort.findById(request.epicId())
                .orElseThrow(() -> new CustomException("Epic not found", HttpStatus.NOT_FOUND));
        Task task = Task.builder()
                .title(request.title())
                .description(request.description())
                .status(request.status())
                .assignee(assignee)
                .startDate(request.startDate())
                .endDate(request.endDate())
                .priority(request.priority())
                .epic(epic)
                .build();

        taskPersistencePort.save(task);
    }

    public void deleteTask(String taskId) {
        taskPersistencePort.delete(taskId);
    }

    public void updateTask(String taskId, UpdateTaskRequest request) {
        User assignee = userPersistencePort.findByEmail(request.assigneeEmail())
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.FORBIDDEN));
        Epic epic = epicPersistencePort.findById(request.epicId())
                .orElseThrow(() -> new CustomException("Epic not found", HttpStatus.NOT_FOUND));
        Task task = Task.builder()
                .title(request.title())
                .description(request.description())
                .status(request.status())
                .assignee(assignee)
                .startDate(request.startDate())
                .endDate(request.endDate())
                .priority(request.priority())
                .epic(epic)
                .build();

        taskPersistencePort.update(taskId, task);
    }
}