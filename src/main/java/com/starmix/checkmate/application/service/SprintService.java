package com.starmix.checkmate.application.service;

import com.starmix.checkmate.adapter.in.sse.sprint.request.CreateSprintRequest;
import com.starmix.checkmate.adapter.in.sse.sprint.request.UpdateSprintRequest;
import com.starmix.checkmate.adapter.in.sse.sprint.response.UpdateSprintResponse;
import com.starmix.checkmate.adapter.out.ai.client.response.CreateSprintFeignResponse;
import com.starmix.checkmate.adapter.out.redis.RedisType;
import com.starmix.checkmate.adapter.out.redis.dto.SprintDetail;
import com.starmix.checkmate.application.port.out.ai.AIPort;
import com.starmix.checkmate.application.port.out.persistence.EpicPersistencePort;
import com.starmix.checkmate.application.port.out.persistence.FeaturePersistencePort;
import com.starmix.checkmate.application.port.out.persistence.SprintPersistencePort;
import com.starmix.checkmate.application.port.out.persistence.TaskPersistencePort;
import com.starmix.checkmate.application.port.out.redis.RedisPort;
import com.starmix.checkmate.domain.epic.Epic;
import com.starmix.checkmate.domain.epic.EpicDetail;
import com.starmix.checkmate.domain.project.Feature;
import com.starmix.checkmate.domain.sprint.Sprint;
import com.starmix.checkmate.domain.task.Task;
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
    private final FeaturePersistencePort featurePersistencePort;

    public List<Sprint> getSprintsByProjectId(String projectId) {
        return sprintPersistencePort.findAllByProjectId(projectId);
    }

    public List<UpdateSprintResponse> createSprint(String projectId, CreateSprintRequest request) {
        CreateSprintFeignResponse response = aIPort.createSprint(projectId, request.pendingTaskIds());
        Integer sequence = sprintPersistencePort.getNextSequence();

        Sprint sprint = Sprint.create(
                response.sprint().title(), response.sprint().description(), sequence,
                projectId, response.sprint().startDate(), response.sprint().endDate()
        );
        List<EpicDetail> epicDetails = response.epics().stream().map(
                epic -> {
                    Epic createdEpic = Epic.create(
                            epic.title(), epic.description(), projectId, sprint.getSprintId()
                    );
                    List<Task> tasks = epic.featureIds().stream().map(
                            featureId -> {
                                Feature feature = featurePersistencePort.findById(featureId)
                                        .orElseThrow(() -> new CustomException("Feature not found", HttpStatus.NOT_FOUND));
                                return Task.fromFeature(feature);
                            }
                    ).toList();
                    return EpicDetail.builder()
                            .epic(createdEpic)
                            .tasks(tasks)
                            .build();
                }
        ).toList();

        SprintDetail sprintDetail = SprintDetail.builder()
                .sprint(sprint)
                .epics(epicDetails.stream().map(EpicDetail::epic).toList())
                .build();
        redisPort.saveObject(RedisType.SPRINT_INFO, projectId, sprintDetail);

        return epicDetails.stream().map(
                epicDetail -> UpdateSprintResponse.fromEpicAndTasks(
                        epicDetail.epic(), epicDetail.tasks()
                )
        ).toList();
    }

    public List<UpdateSprintResponse> updateSprint(
            String projectId, List<UpdateSprintRequest> request
    ) {
        SprintDetail sprintDetail = redisPort.getObject(RedisType.PROJECT_INFO, projectId);

        List<UpdateSprintResponse> response = request.stream().map(requestItem -> {
            Epic epic = epicPersistencePort.findById(requestItem.epicId())
                    .orElseThrow(() -> new CustomException("Epic not found", HttpStatus.NOT_FOUND));
            requestItem.tasks().forEach(
                    task -> {
                        task.updateEpic(epic);
                        taskPersistencePort.save(task);
                    }
            );

            return UpdateSprintResponse.fromEpicAndTasks(epic, requestItem.tasks());
        }).toList();
        sprintDetail.epics().forEach(epicPersistencePort::save);
        sprintPersistencePort.save(sprintDetail.sprint());
        return response;
    }
}