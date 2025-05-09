package com.starmix.checkmate.adapter.out.persistence.adapter;

import com.starmix.checkmate.adapter.out.persistence.entity.ProjectEntity;
import com.starmix.checkmate.adapter.out.persistence.mapper.ProjectMapper;
import com.starmix.checkmate.adapter.out.persistence.mapper.UserMapper;
import com.starmix.checkmate.adapter.out.persistence.mongo.ProjectMongoRepository;
import com.starmix.checkmate.adapter.out.persistence.mongo.UserMongoRepository;
import com.starmix.checkmate.application.port.out.persistence.ProjectPersistencePort;
import com.starmix.checkmate.domain.project.Project;
import com.starmix.checkmate.domain.user.User;
import com.starmix.checkmate.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class ProjectPersistenceAdapter implements ProjectPersistencePort {

    private final ProjectMongoRepository projectMongoRepository;
    private final UserMongoRepository userMongoRepository;

    @Override
    public List<Project> findByMemberIdsContaining(String memberId) {
        try {
            List<ProjectEntity> projectEntities = projectMongoRepository.findByMemberIdsContaining(memberId);
            return projectEntities.stream()
                    .map(this::toProjectWithMembersAndLeader)
                    .toList();
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Optional<Project> findById(String id) {
        try {
            Optional<ProjectEntity> optionalProjectEntity =  projectMongoRepository.findById(id);
            return optionalProjectEntity.map(this::toProjectWithMembersAndLeader);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String save(Project project) {
        try {
            ProjectEntity projectEntity = ProjectMapper.toEntity(project);
            return projectMongoRepository.save(projectEntity).getId();
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Project> findActiveProjects(String memberId) {
        try {
            LocalDate today = LocalDate.now();
            List<ProjectEntity> projectEntities =
                    projectMongoRepository.findByMemberIdsContainingAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                            memberId, today, today
                    );
            return projectEntities.stream().map(this::toProjectWithMembersAndLeader).toList();
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Project> findArchivedProjects(String memberId) {
        try {
            LocalDate today = LocalDate.now();
            List<ProjectEntity> projectEntities = projectMongoRepository.findByMemberIdsContainingAndEndDateBefore(
                    memberId, today
            );
            return projectEntities.stream().map(this::toProjectWithMembersAndLeader).toList();
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Project toProjectWithMembersAndLeader(ProjectEntity projectEntity) {
        List<User> members = projectEntity.getMemberIds().stream()
                .map(userMongoRepository::findById)
                .flatMap(Optional::stream)
                .map(UserMapper::toDomain)
                .toList();

        User leader = userMongoRepository.findById(projectEntity.getLeaderId())
                .map(UserMapper::toDomain)
                .orElseThrow(() -> new CustomException("Leader not found", HttpStatus.INTERNAL_SERVER_ERROR));

        return ProjectMapper.toDomain(projectEntity, leader, members);
    }
}
