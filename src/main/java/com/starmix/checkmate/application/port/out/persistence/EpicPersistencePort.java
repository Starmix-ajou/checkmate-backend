package com.starmix.checkmate.application.port.out.persistence;

import com.starmix.checkmate.domain.epic.Epic;

import java.util.List;
import java.util.Optional;

public interface EpicPersistencePort {
    List<Epic> findAllByProjectId(String projectId);
    Optional<Epic> findById(String id);
}
