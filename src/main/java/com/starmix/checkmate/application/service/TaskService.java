package com.starmix.checkmate.application.service;

import com.starmix.checkmate.adapter.in.rest.web.task.response.TaskResponse;
import com.starmix.checkmate.adapter.in.rest.web.task.request.CreateTaskRequest;
import com.starmix.checkmate.adapter.in.rest.web.task.request.UpdateTaskRequest;
import com.starmix.checkmate.adapter.in.rest.web.task.response.TaskCountResponse;
import com.starmix.checkmate.adapter.in.rest.web.task.response.TaskScheduleResponse;
import com.starmix.checkmate.application.port.out.persistence.*;
import com.starmix.checkmate.domain.epic.Epic;
import com.starmix.checkmate.domain.notification.Notification;
import com.starmix.checkmate.domain.project.Project;
import com.starmix.checkmate.domain.sprint.Sprint;
import com.starmix.checkmate.domain.task.Priority;
import com.starmix.checkmate.domain.task.Status;
import com.starmix.checkmate.domain.task.Task;
import com.starmix.checkmate.domain.user.User;
import com.starmix.checkmate.global.exception.CustomException;
import com.starmix.checkmate.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskService {
    private final TaskPersistencePort taskPersistencePort;
    private final UserPersistencePort userPersistencePort;
    private final EpicPersistencePort epicPersistencePort;
    private final SprintPersistencePort sprintPersistencePort;
    private final JwtUtil jwtUtil;
    private final ProjectPersistencePort projectPersistencePort;
    private final NotificationService notificationService;

    public List<TaskResponse> getTasks(
            String projectId, List<String> epicId, List<String> sprintId,
            List<String> assigneeEmail, List<Priority> priority,
            LocalDate startDate, LocalDate endDate, List<Status> status
    ) {
        List<Task> tasks = taskPersistencePort.filterTasks(
                projectId, epicId, sprintId, assigneeEmail,
                priority, startDate, endDate, status
        );

        return tasks.stream().map(
                task -> {
                    Sprint sprint = findSprintByEpic(task.getEpic());
                    return TaskResponse.fromDomain(task, sprint);
                }
        ).toList();
    }

    public TaskResponse getTask(String taskId) {
        Task task = taskPersistencePort.findById(taskId)
                .orElseThrow(() -> new CustomException("Task not found", HttpStatus.NOT_FOUND));
        Sprint sprint = findSprintByEpic(task.getEpic());
        return TaskResponse.fromDomain(task, sprint);
    }

    @Transactional
    public void createTask(CreateTaskRequest request) {
        User assignee = userPersistencePort.findByEmail(request.assigneeEmail())
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.FORBIDDEN));

        Epic epic = epicPersistencePort.findById(request.epicId())
                .orElseThrow(() -> new CustomException("Epic not found", HttpStatus.NOT_FOUND));

        Task task = Task.create(
                request.title(), request.description(), request.status(), assignee,
                request.startDate(), request.endDate(), request.priority(), epic
        );

        Sprint sprint = sprintPersistencePort.findCurrentSprint(epic.getProjectId())
                        .orElseThrow(() -> new CustomException("Sprint not found", HttpStatus.NOT_FOUND));
        sprint.addEpic(epic);
        sprintPersistencePort.save(sprint);

        taskPersistencePort.save(task);
    }

    public void deleteTask(String taskId) {
        taskPersistencePort.delete(taskId);
    }

    @Transactional
    public void updateTask(String taskId, UpdateTaskRequest request) {
        User assignee = userPersistencePort.findByEmail(request.assigneeEmail())
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.FORBIDDEN));
        Epic epic = epicPersistencePort.findById(request.epicId())
                .orElseThrow(() -> new CustomException("Epic not found", HttpStatus.NOT_FOUND));
        Project project = projectPersistencePort.findById(epic.getProjectId())
                .orElseThrow(() -> new CustomException("Project not found", HttpStatus.NOT_FOUND));
        Task task = taskPersistencePort.findById(taskId)
                .orElseThrow(() -> new CustomException("Task not found", HttpStatus.NOT_FOUND));

        User prevAssignee = task.getAssignee();
        task.update(
                request.title(), request.description(), request.status(), assignee,
                request.startDate(), request.endDate(),
                request.review(), request.priority(), epic
        );
        if(!prevAssignee.equals(task.getAssignee())) {
            Notification notification = Notification.assignTaskNotification(
                    assignee.getUserId(), task, project
            );
            notificationService.addNotifications(notification);
        }
        taskPersistencePort.save(task);
    }

    public List<TaskScheduleResponse> getTaskSchedule(String projectId) {
        String assigneeId = jwtUtil.extractUser().getUserId();
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
        List<Task> tasks = taskPersistencePort.findMyTasksByStartDateAndEndDate(
                projectId, assigneeId, startOfWeek, endOfWeek
        );

        return startOfWeek.datesUntil(endOfWeek.plusDays(1))
                .map(date -> TaskScheduleResponse.fromDomain(
                        Task.filterByDate(tasks, date),
                        date
                )).toList();
    }

    public TaskCountResponse getTaskCount(String projectId) {
        String assigneeId = jwtUtil.extractUser().getUserId();
        List<Task> tasks = taskPersistencePort.findByAssigneeId(projectId, assigneeId);
        return TaskCountResponse.fromDomain(tasks);
    }

    private Sprint findSprintByEpic(Epic epic) {
        List<Sprint> sprints = sprintPersistencePort.findSprintByEpicId(epic.getEpicId());
        return epic.findCurrentSprint(sprints);
    }
}
