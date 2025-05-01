package com.starmix.checkmate.application.service;

import com.starmix.checkmate.adapter.in.http.epic.request.CreateEpicRequest;
import com.starmix.checkmate.application.port.out.persistence.EpicPersistencePort;
import com.starmix.checkmate.domain.epic.Epic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EpicService {

    private final EpicPersistencePort epicPersistencePort;

    public List<Epic> getEpicsByProjectId(String projectId) {
        return epicPersistencePort.findAllByProjectId(projectId);
    }

    public void createEpic(CreateEpicRequest request) {
        Epic epic = Epic.builder()
                .title(request.title())
                .title(request.title())
                .description(request.description())
                .projectId(request.projectId())
                .build();
        epicPersistencePort.save(epic);
    }
}