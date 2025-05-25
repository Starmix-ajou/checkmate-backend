package com.starmix.checkmate.application.service;

import com.starmix.checkmate.adapter.in.http.epic.request.CreateEpicRequest;
import com.starmix.checkmate.adapter.in.http.epic.response.EpicResponse;
import com.starmix.checkmate.application.port.out.persistence.EpicPersistencePort;
import com.starmix.checkmate.application.port.out.persistence.SprintPersistencePort;
import com.starmix.checkmate.application.port.out.persistence.TaskPersistencePort;
import com.starmix.checkmate.domain.epic.Epic;
import com.starmix.checkmate.domain.sprint.Sprint;
import com.starmix.checkmate.domain.task.Task;
import com.starmix.checkmate.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EpicService {

    private final EpicPersistencePort epicPersistencePort;
    private final SprintPersistencePort sprintPersistencePort;
    private final TaskPersistencePort taskPersistencePort;

    public List<EpicResponse> getEpics(String projectId, String sprintId) {
        List<Epic> epics = epicPersistencePort.filterEpics(projectId, sprintId);
        return epics.stream().map(
                epic -> {
                    List<Task> tasks = taskPersistencePort.findAllByEpicId(epic.getEpicId());
                    List<Sprint> sprints = sprintPersistencePort.findSprintByEpicId(epic.getEpicId());
                    return EpicResponse.fromDomain(
                            epic, tasks,
                            epic.findLatestSprint(sprints)
                    );
                }
        ).toList();
    }

    @Transactional
    public void createEpic(String projectId, CreateEpicRequest request) {
        Sprint sprint = sprintPersistencePort.findCurrentSprint(projectId)
                .orElseThrow(() -> new CustomException("Sprint not found", HttpStatus.NOT_FOUND));

        Epic epic = Epic.create(request.title(), request.description(), projectId);
        epicPersistencePort.save(epic);

        sprint.addEpic(epic);
        sprintPersistencePort.save(sprint);
    }

    @Transactional
    public void deleteEpic(String epicId) {
        List<Task> tasks = taskPersistencePort.findAllByEpicId(epicId);
        tasks.forEach(task -> taskPersistencePort.delete(task.getTaskId()));

        Epic epic = epicPersistencePort.findById(epicId)
                        .orElseThrow(() -> new CustomException("Epic not found", HttpStatus.NOT_FOUND));
        List<Sprint> sprints = sprintPersistencePort.findSprintByEpicId(epic.getEpicId());
        sprints.forEach(sprint -> sprintPersistencePort.delete(sprint.getSprintId()));
        epicPersistencePort.delete(epicId);
    }
}
