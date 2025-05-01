package com.starmix.checkmate.adapter.out.persistence.adapter;

import com.starmix.checkmate.adapter.out.persistence.entity.TaskEntity;
import com.starmix.checkmate.adapter.out.persistence.mapper.TaskMapper;
import com.starmix.checkmate.adapter.out.persistence.mongo.TaskMongoRepository;
import com.starmix.checkmate.application.port.out.persistence.TaskPersistencePort;
import com.starmix.checkmate.domain.task.Task;
import com.starmix.checkmate.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class TaskPersistenceAdapter implements TaskPersistencePort {

    private final TaskMongoRepository taskMongoRepository;

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
}
