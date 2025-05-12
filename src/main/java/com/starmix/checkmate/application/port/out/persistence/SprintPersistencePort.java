package com.starmix.checkmate.application.port.out.persistence;

import com.starmix.checkmate.domain.sprint.Sprint;

import java.util.List;
import java.util.Optional;

public interface SprintPersistencePort {
    List<Sprint> findAllByProjectId(String projectId);
    Optional<Sprint> findById(String id);
    String save(Sprint sprint);
    Integer getNextSequence();
}
