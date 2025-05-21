package com.starmix.checkmate.adapter.out.persistence.adapter;

import com.starmix.checkmate.adapter.out.persistence.entity.EpicEntity;
import com.starmix.checkmate.adapter.out.persistence.mapper.EpicMapper;
import com.starmix.checkmate.adapter.out.persistence.mongo.EpicMongoRepository;
import com.starmix.checkmate.application.port.out.persistence.EpicPersistencePort;
import com.starmix.checkmate.application.port.out.persistence.SprintPersistencePort;
import com.starmix.checkmate.domain.epic.Epic;
import com.starmix.checkmate.domain.sprint.Sprint;
import com.starmix.checkmate.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class EpicPersistenceAdapter implements EpicPersistencePort {

    private final EpicMongoRepository epicMongoRepository;
    private final SprintPersistencePort sprintPersistencePort;

    @Override
    public List<Epic> filterEpics(String projectId, String sprintId) {
        try {
            if(sprintId != null && !sprintId.isBlank()) {
                return sprintPersistencePort.findById(sprintId)
                        .map(Sprint::getEpics)
                        .orElseThrow(() -> new CustomException("Sprint not found", HttpStatus.NOT_FOUND));
            }
            List<EpicEntity> epicEntities = epicMongoRepository.findAllByProjectId(projectId);
            return epicEntities.stream().map(EpicMapper::toDomain).toList();
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Optional<Epic> findById(String id) {
        try {
            Optional<EpicEntity> epicEntity = epicMongoRepository.findById(id);
            return epicEntity.map(EpicMapper::toDomain);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String save(Epic epic) {
        try {
            EpicEntity epicEntity = EpicMapper.toEntity(epic);
            return epicMongoRepository.save(epicEntity).getId();
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void delete(String id) {
        try {
            epicMongoRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
