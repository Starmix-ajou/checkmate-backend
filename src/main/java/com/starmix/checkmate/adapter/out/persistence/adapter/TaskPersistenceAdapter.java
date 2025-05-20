package com.starmix.checkmate.adapter.out.persistence.adapter;

import com.starmix.checkmate.adapter.out.persistence.entity.EpicEntity;
import com.starmix.checkmate.adapter.out.persistence.entity.TaskEntity;
import com.starmix.checkmate.adapter.out.persistence.entity.UserEntity;
import com.starmix.checkmate.adapter.out.persistence.mapper.TaskMapper;
import com.starmix.checkmate.adapter.out.persistence.mongo.TaskMongoRepository;
import com.starmix.checkmate.application.port.out.persistence.TaskPersistencePort;
import com.starmix.checkmate.domain.task.Priority;
import com.starmix.checkmate.domain.task.Status;
import com.starmix.checkmate.domain.task.Task;
import com.starmix.checkmate.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class TaskPersistenceAdapter implements TaskPersistencePort {

    private final TaskMongoRepository taskMongoRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public Optional<Task> findById(String id) {
        try {
            Optional<TaskEntity> taskEntity = taskMongoRepository.findById(id);
            return taskEntity.map(TaskMapper::toDomain);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String save(Task task) {
        try {
            TaskEntity taskEntity = TaskMapper.toEntity(task);
            return taskMongoRepository.save(taskEntity).getId();
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void delete(String id) {
        try {
            taskMongoRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Task> filterTasks(
            String projectId, List<String> epicIds, List<String> sprintIds,
            List<String> assigneeEmails, List<Priority> priorities,
            LocalDate startDate, LocalDate endDate, List<Status> status
    ) {
        try {
            List<Criteria> taskCriteriaList = new ArrayList<>();

            if (epicIds != null || projectId != null || sprintIds != null) {
                List<Criteria> epicCriteriaList = new ArrayList<>();

                if (epicIds != null && !epicIds.isEmpty()) {
                    epicCriteriaList.add(Criteria.where("_id").in(epicIds));
                }

                if (projectId != null) {
                    epicCriteriaList.add(Criteria.where("projectId").is(projectId));
                }

                if (sprintIds != null && !sprintIds.isEmpty()) {
                    epicCriteriaList.add(Criteria.where("sprintId").in(sprintIds));
                }

                Query epicQuery = new Query(new Criteria().andOperator(epicCriteriaList.toArray(new Criteria[0])));
                List<EpicEntity> epics = mongoTemplate.find(epicQuery, EpicEntity.class);

                if (epics.isEmpty()) {
                    return Collections.emptyList();
                }

                List<String> epicEntityIds = epics.stream().map(EpicEntity::getId).toList();

                taskCriteriaList.add(Criteria.where("epic.$id").in(epicEntityIds));
            }

            if (assigneeEmails != null && !assigneeEmails.isEmpty()) {
                Query userQuery = new Query(Criteria.where("email").in(assigneeEmails));
                List<UserEntity> users = mongoTemplate.find(userQuery, UserEntity.class);

                if (users.isEmpty()) {
                    return Collections.emptyList();
                }

                List<ObjectId> userIds = users.stream()
                        .map(u -> new ObjectId(u.getId()))
                        .toList();

                taskCriteriaList.add(Criteria.where("assignee.$id").in(userIds));
            }

            if (priorities != null && !priorities.isEmpty()) {
                List<Integer> priorityNums = priorities.stream()
                        .map(Priority::getPriorityNum)
                        .toList();
                taskCriteriaList.add(Criteria.where("priority").in(priorityNums));
            }

            if (startDate != null) {
                taskCriteriaList.add(Criteria.where("startDate").gte(startDate));
            }

            if (endDate != null) {
                taskCriteriaList.add(Criteria.where("endDate").lte(endDate));
            }

            if (status != null && !status.isEmpty()) {
                taskCriteriaList.add(Criteria.where("status").in(status));
            }

            Query taskQuery = new Query();
            if (!taskCriteriaList.isEmpty()) {
                taskQuery.addCriteria(new Criteria().andOperator(taskCriteriaList.toArray(new Criteria[0])));
            }

            List<TaskEntity> taskEntities = mongoTemplate.find(taskQuery, TaskEntity.class);

            return taskEntities.stream()
                    .map(TaskMapper::toDomain)
                    .toList();
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Task> findByAssigneeId(String projectId, String assigneeId) {
        try {
            List<Criteria> taskCriteriaList = new ArrayList<>();

            if (projectId != null) {
                Query epicQuery = new Query(Criteria.where("projectId").is(projectId));
                List<EpicEntity> epics = mongoTemplate.find(epicQuery, EpicEntity.class);

                if (epics.isEmpty()) {
                    return Collections.emptyList();
                }

                List<String> epicIds = epics.stream().map(EpicEntity::getId).toList();
                taskCriteriaList.add(Criteria.where("epic.$id").in(epicIds));
            }

            if (assigneeId != null) {
                taskCriteriaList.add(Criteria.where("assignee.$id").is(new ObjectId(assigneeId)));
            }

            Query taskQuery = new Query();
            if (!taskCriteriaList.isEmpty()) {
                taskQuery.addCriteria(new Criteria().andOperator(taskCriteriaList.toArray(new Criteria[0])));
            }

            List<TaskEntity> taskEntities = mongoTemplate.find(taskQuery, TaskEntity.class);
            return taskEntities.stream().map(TaskMapper::toDomain).toList();
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Task> findMyTasksByStartDateAndEndDate(
            String projectId, String assigneeId, LocalDate startDate, LocalDate endDate
    ) {
        try {
            List<Criteria> criteriaList = new ArrayList<>();

            criteriaList.add(Criteria.where("startDate").lte(endDate));
            criteriaList.add(Criteria.where("endDate").gte(startDate));

            if (assigneeId != null) {
                criteriaList.add(Criteria.where("assignee.$id").is(new ObjectId(assigneeId)));
            }

            if (projectId != null) {
                Query epicQuery = new Query(Criteria.where("projectId").is(projectId));
                List<EpicEntity> epics = mongoTemplate.find(epicQuery, EpicEntity.class);

                if (epics.isEmpty()) {
                    return Collections.emptyList();
                }

                List<String> epicIds = epics.stream().map(EpicEntity::getId).toList();
                criteriaList.add(Criteria.where("epic.$id").in(epicIds));
            }

            Query query = new Query(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
            List<TaskEntity> tasks = mongoTemplate.find(query, TaskEntity.class);

            return tasks.stream().map(TaskMapper::toDomain).toList();
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}