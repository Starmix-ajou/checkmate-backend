package com.starmix.checkmate.adapter.in.task;

import com.starmix.checkmate.application.service.TaskService;
import com.starmix.checkmate.domain.task.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> getTasks () {
        List<Task> tasks = taskService.getTasks();
        return ResponseEntity.ok().body(tasks);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTask (@PathVariable String taskId) {
        Task task = taskService.getTask(taskId);
        return ResponseEntity.ok().body(task);
    }
}