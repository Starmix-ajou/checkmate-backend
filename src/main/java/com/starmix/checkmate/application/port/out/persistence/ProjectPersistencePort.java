package com.starmix.checkmate.application.port.out.persistence;

import com.starmix.checkmate.domain.project.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectPersistencePort {
    Optional<Project> findById(String id);
    String save(Project project);
    void delete(String projectId);
    List<Project> findByProjectIds(List<String> projectIds);
}
