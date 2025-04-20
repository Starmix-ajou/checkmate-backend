package com.starmix.checkmate.application.port.out.persistence;

import com.starmix.checkmate.domain.project.Project;
import com.starmix.checkmate.domain.sprint.Sprint;

import java.util.List;

public interface ProjectPersistencePort {
    List<Project> findByMembersEmail(String email);
}
