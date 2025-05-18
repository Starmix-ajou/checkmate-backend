package com.starmix.checkmate.adapter.in.http.task.response;

import com.starmix.checkmate.domain.task.Status;
import com.starmix.checkmate.domain.task.Task;
import lombok.Builder;

import java.util.List;

@Builder
public record TaskCountResponse(
        TaskCount total,
        TaskCount todo,
        TaskCount inProgress,
        TaskCount done
) {
    @Builder
    public record TaskCount(
            Integer count,
            List<TaskScheduleBrief> tasks
    ) { }

    public static TaskCountResponse fromDomain(List<Task> tasks) {
        TaskCount total = TaskCount.builder()
                .count(tasks.size())
                .tasks(tasks.stream().map(TaskScheduleBrief::fromDomain).toList())
                .build();

        List<Task> todoTasks = tasks.stream()
                .filter( task -> task.getStatus().equals(Status.TODO)).toList();
        TaskCount todo = TaskCount.builder()
                .count(todoTasks.size())
                .tasks(todoTasks.stream().map(TaskScheduleBrief::fromDomain).toList())
                .build();

        List<Task> inProgressTasks = tasks.stream()
                .filter( task -> task.getStatus().equals(Status.IN_PROGRESS)).toList();
        TaskCount inProgress = TaskCount.builder()
                .count(inProgressTasks.size())
                .tasks(inProgressTasks.stream().map(TaskScheduleBrief::fromDomain).toList())
                .build();

        List<Task> doneTasks = tasks.stream()
                .filter( task -> task.getStatus().equals(Status.DONE)).toList();
        TaskCount done = TaskCount.builder()
                .count(doneTasks.size())
                .tasks(doneTasks.stream().map(TaskScheduleBrief::fromDomain).toList())
                .build();

        return TaskCountResponse.builder()
                .total(total)
                .todo(todo)
                .inProgress(inProgress)
                .done(done)
                .build();
    }
}