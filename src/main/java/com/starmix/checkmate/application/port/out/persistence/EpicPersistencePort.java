package com.starmix.checkmate.application.port.out.persistence;

import com.starmix.checkmate.domain.epic.Epic;

import java.util.List;
import java.util.Optional;

public interface EpicPersistencePort {
    List<Epic> filterEpics(String projectId, String sprintId);
    Optional<Epic> findById(String id);
    String save(Epic epic);
    void delete(String id);
}
