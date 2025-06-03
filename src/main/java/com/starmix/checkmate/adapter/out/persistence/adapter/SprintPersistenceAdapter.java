package com.starmix.checkmate.adapter.out.persistence.adapter;

import com.starmix.checkmate.adapter.out.persistence.entity.SprintEntity;
import com.starmix.checkmate.adapter.out.persistence.mapper.SprintMapper;
import com.starmix.checkmate.adapter.out.persistence.mongo.SprintMongoRepository;
import com.starmix.checkmate.application.port.out.persistence.SprintPersistencePort;
import com.starmix.checkmate.domain.sprint.Sprint;
import com.starmix.checkmate.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class SprintPersistenceAdapter implements SprintPersistencePort {

    private final SprintMongoRepository sprintMongoRepository;

    @Override
    public List<Sprint> findAllByProjectId(String projectId) {
        try {
            List<SprintEntity> sprintEntities = sprintMongoRepository.findAllByProjectId(projectId);
            return sprintEntities.stream().map(SprintMapper::toDomain).toList();
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Optional<Sprint> findById(String id) {
        try {
            Optional<SprintEntity> sprintEntity = sprintMongoRepository.findById(id);
            return sprintEntity.map(SprintMapper::toDomain);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String save(Sprint sprint) {
        try {
            SprintEntity sprintEntity = SprintMapper.toEntity(sprint);
            return sprintMongoRepository.save(sprintEntity).getId();
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Integer getNextSequence(String projectId) {
        try {
            long sequence = sprintMongoRepository.countByProjectId(projectId);
            return (int) sequence + 1;
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Optional<Sprint> findCurrentSprint(String projectId) {
        try {
            Optional<SprintEntity> sprintEntity = sprintMongoRepository
                    .findTopByProjectIdOrderBySequenceDesc(projectId);
            return sprintEntity.map(SprintMapper::toDomain);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Sprint> findSprintByEpicId(String epicId) {
        try {
            List<SprintEntity> sprintEntities = sprintMongoRepository.findByEpics_Id(epicId);
            return sprintEntities.stream().map(SprintMapper::toDomain).toList();
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void delete(String id) {
        try {
            sprintMongoRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
