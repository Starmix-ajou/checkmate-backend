package com.starmix.checkmate.adapter.out.persistence.adapter;

import com.starmix.checkmate.adapter.out.persistence.entity.TaskEntity;
import com.starmix.checkmate.adapter.out.persistence.mapper.TaskMapper;
import com.starmix.checkmate.adapter.out.persistence.mongo.TaskMongoRepository;
import com.starmix.checkmate.application.port.out.persistence.TaskPersistencePort;
import com.starmix.checkmate.domain.task.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class TaskPersistenceAdapter implements TaskPersistencePort {

    private final TaskMongoRepository taskMongoRepository;

    @Override
    public List<Task> findAll() {
        List<TaskEntity> tasks = taskMongoRepository.findAll();
        return tasks.stream().map(TaskMapper::toDomain).toList();
    }

    @Override
    public Optional<Task> findById(String id) {
        Optional<TaskEntity> taskEntity = taskMongoRepository.findById(id);
        return taskEntity.map(TaskMapper::toDomain);
    }
}
