package com.starmix.checkmate.application.port.out.persistence;

import com.starmix.checkmate.domain.project.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectPersistencePort {
    List<Project> findByMembersEmail(String email);
    Optional<Project> findById(String id);
    void save(Project project);
    List<Project> findActiveProjects();
    List<Project> findArchivedProjects();
}
