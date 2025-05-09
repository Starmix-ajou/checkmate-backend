package com.starmix.checkmate.adapter.out.persistence.adapter;

import com.starmix.checkmate.adapter.out.persistence.entity.EpicEntity;
import com.starmix.checkmate.adapter.out.persistence.entity.TaskEntity;
import com.starmix.checkmate.adapter.out.persistence.entity.UserEntity;
import com.starmix.checkmate.adapter.out.persistence.mapper.TaskMapper;
import com.starmix.checkmate.adapter.out.persistence.mongo.TaskMongoRepository;
import com.starmix.checkmate.application.port.out.persistence.TaskPersistencePort;
import com.starmix.checkmate.domain.task.Priority;
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
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class TaskPersistenceAdapter implements TaskPersistencePort {

    private final TaskMongoRepository taskMongoRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public List<Task> findAll() {
        try {
            List<TaskEntity> tasks = taskMongoRepository.findAll();
            return tasks.stream().map(TaskMapper::toDomain).toList();
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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
    public String update(String id, Task task) {
        try {
            TaskEntity taskEntity = taskMongoRepository.findById(id)
                    .orElseThrow(() -> new CustomException("Task not found", HttpStatus.NOT_FOUND));
            TaskEntity updateTaskEntity = TaskMapper.updateEntity(taskEntity, task);
            return taskMongoRepository.save(updateTaskEntity).getId();
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
            String projectId, String epicId, String sprintId,
            String assigneeEmail, Priority priority,
            LocalDate startDate, LocalDate endDate
    ) {
        try {
            List<Criteria> taskCriteriaList = new ArrayList<>();

            if (epicId != null || projectId != null || sprintId != null) {
                List<Criteria> epicCriteriaList = new ArrayList<>();
                if (epicId != null) {
                    epicCriteriaList.add(Criteria.where("_id").is(new ObjectId(epicId)));
                }
                if (projectId != null) {
                    epicCriteriaList.add(Criteria.where("projectId").is(projectId));
                }
                if (sprintId != null) {
                    epicCriteriaList.add(Criteria.where("sprintId").is(sprintId));
                }

                Query epicQuery = new Query(new Criteria().andOperator(epicCriteriaList.toArray(new Criteria[0])));
                List<EpicEntity> epics = mongoTemplate.find(epicQuery, EpicEntity.class);

                if (epics.isEmpty()) {
                    return Collections.emptyList();
                }

                List<ObjectId> epicObjectIds = epics.stream()
                        .map(e -> new ObjectId(e.getId()))
                        .collect(Collectors.toList());

                taskCriteriaList.add(Criteria.where("epic.$id").in(epicObjectIds));
            }

            if (assigneeEmail != null) {
                Query userQuery = new Query(Criteria.where("email").is(assigneeEmail));
                UserEntity user = mongoTemplate.findOne(userQuery, UserEntity.class);
                if (user != null) {
                    taskCriteriaList.add(Criteria.where("assignee.$id").is(new ObjectId(user.getId())));
                } else {
                    return Collections.emptyList();
                }
            }

            if (priority != null) {
                taskCriteriaList.add(Criteria.where("priority").is(priority.getPriorityNum()));
            }

            if (startDate != null) {
                taskCriteriaList.add(Criteria.where("startDate").gte(startDate));
            }

            if (endDate != null) {
                taskCriteriaList.add(Criteria.where("endDate").lte(endDate));
            }

            Query taskQuery = new Query();
            if (!taskCriteriaList.isEmpty()) {
                taskQuery.addCriteria(new Criteria().andOperator(taskCriteriaList.toArray(new Criteria[0])));
            }

            List<TaskEntity> taskEntities = mongoTemplate.find(taskQuery, TaskEntity.class);

            return taskEntities.stream()
                    .map(TaskMapper::toDomain)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
