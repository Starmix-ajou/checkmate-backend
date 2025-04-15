package com.starmix.checkmate.adapter.out.persistence.adapter;

import com.starmix.checkmate.adapter.out.persistence.entity.SprintEntity;
import com.starmix.checkmate.adapter.out.persistence.mapper.SprintMapper;
import com.starmix.checkmate.adapter.out.persistence.mongo.SprintMongoRepository;
import com.starmix.checkmate.application.port.out.persistence.SprintPersistencePort;
import com.starmix.checkmate.domain.sprint.Sprint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class SprintPersistenceAdapter implements SprintPersistencePort {

    private final SprintMongoRepository sprintMongoRepository;

    @Override
    public List<Sprint> findAllByProjectId(String projectId) {
        List<SprintEntity> sprintEntities = sprintMongoRepository.findAllByProjectId(projectId);
        return sprintEntities.stream().map(SprintMapper::toDomain).toList();
    }
}
