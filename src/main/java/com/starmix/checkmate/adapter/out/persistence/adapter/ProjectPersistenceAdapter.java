package com.starmix.checkmate.adapter.out.persistence.adapter;

import com.starmix.checkmate.adapter.out.persistence.entity.*;
import com.starmix.checkmate.adapter.out.persistence.mapper.ProjectMapper;
import com.starmix.checkmate.adapter.out.persistence.mongo.ProjectMongoRepository;
import com.starmix.checkmate.application.port.out.persistence.ProjectPersistencePort;
import com.starmix.checkmate.domain.project.Project;
import com.starmix.checkmate.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class ProjectPersistenceAdapter implements ProjectPersistencePort {

    private final ProjectMongoRepository projectMongoRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public Optional<Project> findById(String id) {
        try {
            Optional<ProjectEntity> optionalProjectEntity =  projectMongoRepository.findById(id);
            return optionalProjectEntity.map(ProjectMapper::toDomain);
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
    public void delete(String projectId) {
        try {
            Query epicQuery = new Query(Criteria.where("projectId").is(projectId));
            List<EpicEntity> epics = mongoTemplate.find(epicQuery, EpicEntity.class);

            List<String> epicIds = epics.stream().map(EpicEntity::getId).toList();

            if (!epicIds.isEmpty()) {
                Query taskQuery = new Query(Criteria.where("epic.$id").in(epicIds));
                mongoTemplate.remove(taskQuery, TaskEntity.class);
            }

            mongoTemplate.remove(epicQuery, EpicEntity.class);

            Query sprintQuery = new Query(Criteria.where("projectId").is(projectId));
            mongoTemplate.remove(sprintQuery, SprintEntity.class);

            Query meetingQuery = new Query(Criteria.where("projectId").is(projectId));
            mongoTemplate.remove(meetingQuery, MeetingEntity.class);

            Query featureQuery = new Query(Criteria.where("projectId").is(projectId));
            mongoTemplate.remove(featureQuery, FeatureEntity.class);

            Query dailyScrumQuery = new Query(Criteria.where("projectId").is(projectId));
            mongoTemplate.remove(dailyScrumQuery, DailyScrumEntity.class);

            Query userQuery = new Query(Criteria.where("profiles.projectId").is(projectId));
            Update update = new Update().pull("profiles", Query.query(Criteria.where("projectId").is(projectId)).getQueryObject());
            mongoTemplate.updateMulti(userQuery, update, UserEntity.class);

            Query notificationQuery = new Query(Criteria.where("project.$id").is(projectId));
            mongoTemplate.remove(notificationQuery, NotificationEntity.class);

            projectMongoRepository.deleteById(projectId);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Project> findByProjectIds(List<String> projectIds) {
        try {
            List<ProjectEntity> projectEntities = projectMongoRepository.findAllById(projectIds);
            return projectEntities.stream().map(ProjectMapper::toDomain).toList();
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Project> findAll() {
        try {
            List<ProjectEntity> projectEntities = projectMongoRepository.findAll();
            return projectEntities.stream().map(ProjectMapper::toDomain).toList();
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
