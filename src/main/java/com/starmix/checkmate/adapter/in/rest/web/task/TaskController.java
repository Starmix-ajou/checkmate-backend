package com.starmix.checkmate.adapter.in.rest.web.task;

import com.starmix.checkmate.adapter.in.rest.web.task.response.TaskResponse;
import com.starmix.checkmate.adapter.in.rest.web.task.request.CreateTaskRequest;
import com.starmix.checkmate.adapter.in.rest.web.task.request.UpdateTaskRequest;
import com.starmix.checkmate.adapter.in.rest.web.task.response.TaskCountResponse;
import com.starmix.checkmate.adapter.in.rest.web.task.response.TaskScheduleResponse;
import com.starmix.checkmate.application.service.TaskService;
import com.starmix.checkmate.domain.task.Priority;
import com.starmix.checkmate.domain.task.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasks(
            @RequestParam String projectId,
            @RequestParam(required = false) List<String> epicId,
            @RequestParam(required = false) List<String> sprintId,
            @RequestParam(required = false) List<String> assigneeEmail,
            @RequestParam(required = false) List<Priority> priority,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) List<Status> status
    ) {
        List<TaskResponse> tasks = taskService.getTasks(
                projectId, epicId, sprintId, assigneeEmail,
                priority, startDate, endDate, status
        );
        return ResponseEntity.ok().body(tasks);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable String taskId) {
        TaskResponse task = taskService.getTask(taskId);
        return ResponseEntity.ok().body(task);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable String taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok().body(null);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Void> updateTask(@PathVariable String taskId, @RequestBody UpdateTaskRequest request) {
        taskService.updateTask(taskId, request);
        return ResponseEntity.ok().body(null);
    }

    @PostMapping
    public ResponseEntity<Void> createTask(@RequestBody CreateTaskRequest request) {
        taskService.createTask(request);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/schedule")
    public ResponseEntity<List<TaskScheduleResponse>> getTaskSchedule(
            @RequestParam String projectId
    ) {
        List<TaskScheduleResponse> response = taskService.getTaskSchedule(projectId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/count")
    public ResponseEntity<TaskCountResponse> getTaskCount(
            @RequestParam String projectId
    ) {
        TaskCountResponse response = taskService.getTaskCount(projectId);
        return ResponseEntity.ok().body(response);
    }
}