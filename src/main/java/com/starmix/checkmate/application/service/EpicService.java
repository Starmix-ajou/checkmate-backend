package com.starmix.checkmate.application.service;

import com.starmix.checkmate.adapter.in.http.epic.request.CreateEpicRequest;
import com.starmix.checkmate.application.port.out.persistence.EpicPersistencePort;
import com.starmix.checkmate.application.port.out.persistence.SprintPersistencePort;
import com.starmix.checkmate.domain.epic.Epic;
import com.starmix.checkmate.domain.sprint.Sprint;
import com.starmix.checkmate.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EpicService {

    private final EpicPersistencePort epicPersistencePort;
    private final SprintPersistencePort sprintPersistencePort;

    public List<Epic> getEpicsByProjectId(String projectId) {
        return epicPersistencePort.findAllByProjectId(projectId);
    }

    public void createEpic(String projectId, CreateEpicRequest request) {
        Sprint sprint = sprintPersistencePort.findCurrentSprint(projectId)
                .orElseThrow(() -> new CustomException("Epic not found", HttpStatus.NOT_FOUND));

        Epic epic = Epic.builder()
                .title(request.title())
                .title(request.title())
                .description(request.description())
                .projectId(projectId)
                .build();
        epicPersistencePort.save(epic);

        sprint.addEpic(epic);
        sprintPersistencePort.save(sprint);
    }
}