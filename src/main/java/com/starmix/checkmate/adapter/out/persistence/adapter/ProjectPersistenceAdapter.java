package com.starmix.checkmate.adapter.out.persistence.adapter;

import com.starmix.checkmate.adapter.out.persistence.entity.ProjectEntity;
import com.starmix.checkmate.adapter.out.persistence.mapper.ProjectMapper;
import com.starmix.checkmate.adapter.out.persistence.mongo.ProjectMongoRepository;
import com.starmix.checkmate.application.port.out.persistence.ProjectPersistencePort;
import com.starmix.checkmate.domain.project.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class ProjectPersistenceAdapter implements ProjectPersistencePort {

    private final ProjectMongoRepository projectMongoRepository;

    @Override
    public List<Project> findByMembersEmail(String email) {
        List<ProjectEntity> projectEntities = projectMongoRepository.findByMembersEmail(email);
        return projectEntities.stream().map(ProjectMapper::toDomain).toList();
    }

    @Override
    public Optional<Project> findById(String id) {
        Optional<ProjectEntity> projectEntity =  projectMongoRepository.findById(id);
        return projectEntity.map(ProjectMapper::toDomain);
    }

    @Override
    public void save(Project project) {
        ProjectEntity projectEntity = ProjectMapper.toEntity(project);
        projectMongoRepository.save(projectEntity);
    }

    @Override
    public List<Project> findActiveProjects() {
        LocalDate today = LocalDate.now();
        List<ProjectEntity> projectEntities =
                projectMongoRepository.findByStartDateLessThanEqualAndEndDateGreaterThanEqual(today, today);
        return projectEntities.stream().map(ProjectMapper::toDomain).toList();
    }

    @Override
    public List<Project> findArchivedProjects() {
        LocalDate today = LocalDate.now();
        List<ProjectEntity> projectEntities = projectMongoRepository.findByEndDateBefore(today);
        return projectEntities.stream().map(ProjectMapper::toDomain).toList();
    }
}
