package com.starmix.checkmate.adapter.in.http.task;

import com.starmix.checkmate.adapter.in.common.TaskDto;
import com.starmix.checkmate.adapter.in.http.task.request.CreateTaskRequest;
import com.starmix.checkmate.adapter.in.http.task.request.UpdateTaskRequest;
import com.starmix.checkmate.adapter.out.persistence.mapper.TaskMapper;
import com.starmix.checkmate.application.service.TaskService;
import com.starmix.checkmate.domain.task.Priority;
import com.starmix.checkmate.domain.task.Task;
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
    public ResponseEntity<List<TaskDto>> getTasks (
            @RequestParam String projectId,
            @RequestParam(required = false) List<String> epicId,
            @RequestParam(required = false) List<String> sprintId,
            @RequestParam(required = false) List<String> assigneeEmail,
            @RequestParam(required = false) List<Priority> priority,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {
        List<TaskDto> tasks = taskService.getTasks(
                projectId, epicId, sprintId, assigneeEmail,
                priority, startDate, endDate
        );
        return ResponseEntity.ok().body(tasks);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDto> getTask (@PathVariable String taskId) {
        TaskDto task = taskService.getTask(taskId);
        return ResponseEntity.ok().body(task);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask (@PathVariable String taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok().body(null);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Void> updateTask (@PathVariable String taskId, @RequestBody UpdateTaskRequest request) {
        taskService.updateTask(taskId, request);
        return ResponseEntity.ok().body(null);
    }

    @PostMapping
    public ResponseEntity<Void> createTask (@RequestBody CreateTaskRequest request) {
        taskService.createTask(request);
        return ResponseEntity.ok().body(null);
    }


}