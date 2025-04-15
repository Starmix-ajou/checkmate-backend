package com.starmix.checkmate.application.port.out.persistence;

import com.starmix.checkmate.domain.epic.Epic;

import java.util.List;

public interface EpicPersistencePort {
    List<Epic> findAllByProjectId(String projectId);
}
