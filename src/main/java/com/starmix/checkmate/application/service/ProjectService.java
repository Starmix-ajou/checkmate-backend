package com.starmix.checkmate.application.service;

import com.starmix.checkmate.application.port.out.persistence.ProjectPersistencePort;
import com.starmix.checkmate.domain.project.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectPersistencePort projectPersistencePort;

    public List<Project> getProjects() {

        // TODO: JWT 파싱해서 본인 확인 로직 추가

        return projectPersistencePort.findAll();
    }
}