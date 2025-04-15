package com.starmix.checkmate.adapter.out.persistence.adapter;

import com.starmix.checkmate.adapter.out.persistence.entity.EpicEntity;
import com.starmix.checkmate.adapter.out.persistence.mapper.EpicMapper;
import com.starmix.checkmate.adapter.out.persistence.mongo.EpicMongoRepository;
import com.starmix.checkmate.application.port.out.persistence.EpicPersistencePort;
import com.starmix.checkmate.domain.Epic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class EpicPersistenceAdapter implements EpicPersistencePort {

    private final EpicMongoRepository epicMongoRepository;

    @Override
    public List<Epic> findByProjectId(String projectId) {
        List<EpicEntity> epicEntities = epicMongoRepository.findAllByProjectId(projectId);
        return epicEntities.stream().map(EpicMapper::toDomain).toList();
    }
}
