package com.starmix.checkmate.application.port.out.persistence;

import com.starmix.checkmate.domain.sprint.Sprint;

import java.util.List;

public interface SprintPersistencePort {
    List<Sprint> finAllByProjectId(String projectId);
}
