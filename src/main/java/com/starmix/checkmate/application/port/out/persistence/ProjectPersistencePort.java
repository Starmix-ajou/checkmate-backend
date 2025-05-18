package com.starmix.checkmate.application.port.out.persistence;

import com.starmix.checkmate.domain.project.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectPersistencePort {
    List<Project> findByMemberIdsContaining(String memberId);
    Optional<Project> findById(String id);
    String save(Project project);
    List<Project> findActiveProjects(String memberId);
    List<Project> findArchivedProjects(String memberId);
    void delete(String projectId);
}
