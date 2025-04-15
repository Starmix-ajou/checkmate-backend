package com.starmix.checkmate.application.port.out.persistence;

import com.starmix.checkmate.domain.Epic;

import java.util.List;

public interface EpicPersistencePort {
    List<Epic> findByProjectId(String projectId);
}
