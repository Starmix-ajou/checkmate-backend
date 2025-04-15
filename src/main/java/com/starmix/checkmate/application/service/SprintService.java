package com.starmix.checkmate.application.service;

import com.starmix.checkmate.application.port.out.persistence.SprintPersistencePort;
import com.starmix.checkmate.domain.sprint.Sprint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SprintService {

    private final SprintPersistencePort sprintPersistencePort;

    public List<Sprint> getSprintsByProjectId(String projectId) {
        return sprintPersistencePort.finAllByProjectId(projectId);
    }
}