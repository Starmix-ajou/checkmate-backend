package com.starmix.checkmate.application.service;

import com.starmix.checkmate.adapter.in.sse.sprint.request.CreateSprintRequest;
import com.starmix.checkmate.adapter.in.sse.sprint.request.UpdateSprintRequest;
import com.starmix.checkmate.adapter.in.sse.sprint.response.UpdateSprintResponse;
import com.starmix.checkmate.adapter.out.ai.client.response.CreateSprintFeignResponse;
import com.starmix.checkmate.adapter.out.redis.RedisType;
import com.starmix.checkmate.adapter.out.redis.dto.SprintDetail;
import com.starmix.checkmate.application.port.out.ai.AIPort;
import com.starmix.checkmate.application.port.out.persistence.*;
import com.starmix.checkmate.application.port.out.redis.RedisPort;
import com.starmix.checkmate.domain.epic.Epic;
import com.starmix.checkmate.domain.sprint.Sprint;
import com.starmix.checkmate.domain.task.Priority;
import com.starmix.checkmate.domain.task.Task;
import com.starmix.checkmate.domain.user.User;
import com.starmix.checkmate.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SprintService {

    private final SprintPersistencePort sprintPersistencePort;
    private final TaskPersistencePort taskPersistencePort;
    private final RedisPort redisPort;
    private final AIPort aIPort;
    private final EpicPersistencePort epicPersistencePort;
    private final UserPersistencePort userPersistencePort;

    public List<Sprint> getSprintsByProjectId(String projectId) {
        return sprintPersistencePort.findAllByProjectId(projectId);
    }

    public List<UpdateSprintResponse> createSprint(String projectId, CreateSprintRequest request) {
        CreateSprintFeignResponse response = aIPort.createSprint(projectId, request.pendingTaskIds());
        Integer sequence = sprintPersistencePort.getNextSequence(projectId);

        Sprint sprint = Sprint.create(
                response.sprint().title(), response.sprint().description(), sequence,
                projectId, response.sprint().startDate(), response.sprint().endDate()
        );
        List<UpdateSprintResponse> updateSprintResponses = response.epics().stream().map(epicWithFeatures -> {
            Epic epic = epicPersistencePort.findById(epicWithFeatures.epicId())
                    .orElseThrow(() -> new CustomException("Epic not found", HttpStatus.NOT_FOUND));
            List<Task> tasks = epicWithFeatures.tasks().stream().map(taskBrief -> {
                User assignee = userPersistencePort.findById(taskBrief.assigneeId())
                        .orElseThrow(() -> new CustomException("User not found", HttpStatus.FORBIDDEN));
                return Task.init(
                        taskBrief.title(), taskBrief.description(), assignee, taskBrief.startDate(),
                        taskBrief.endDate(), Priority.getPriority(taskBrief.priority()), epic
                );
            }).toList();
            return UpdateSprintResponse.fromEpicAndTasks(epic, tasks);
        }).toList();

        SprintDetail sprintDetail = SprintDetail.builder()
                .sprint(sprint)
                .epics(updateSprintResponses.stream().map(UpdateSprintResponse::epic).toList())
                .build();
        redisPort.saveObject(RedisType.SPRINT_INFO, projectId, sprintDetail);

        return updateSprintResponses;
    }

    public List<UpdateSprintResponse> updateSprint(
            String projectId, List<UpdateSprintRequest> request
    ) {
        SprintDetail sprintDetail = redisPort.getObject(RedisType.PROJECT_INFO, projectId);

        List<UpdateSprintResponse> response = request.stream().map(requestItem -> {
            Epic epic = epicPersistencePort.findById(requestItem.epicId())
                    .orElseThrow(() -> new CustomException("Epic not found", HttpStatus.NOT_FOUND));
            List<Task> tasks = requestItem.tasks().stream().map(
                    task -> {
                        User assignee = userPersistencePort.findByEmail(task.assigneeEmail())
                                .orElseThrow(() -> new CustomException("User not found", HttpStatus.FORBIDDEN));
                        Task createdTask = Task.init(
                                task.title(), task.description(), assignee,
                                task.startDate(), task.endDate(), task.priority(), epic
                        );
                        taskPersistencePort.save(createdTask);
                        return createdTask;
                    }
            ).toList();
            return UpdateSprintResponse.fromEpicAndTasks(epic, tasks);
        }).toList();
        sprintDetail.epics().forEach(epicPersistencePort::save);
        sprintPersistencePort.save(sprintDetail.sprint());
        return response;
    }
}