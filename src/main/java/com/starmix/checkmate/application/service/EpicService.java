package com.starmix.checkmate.application.service;

import com.starmix.checkmate.application.port.out.persistence.EpicPersistencePort;
import com.starmix.checkmate.domain.Epic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EpicService {

    private final EpicPersistencePort epicPersistencePort;

    public List<Epic> getEpicsByProjectId(String projectId) {
        return epicPersistencePort.finAllByProjectId(projectId);
    }
}