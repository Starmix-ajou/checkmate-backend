package com.starmix.checkmate.adapter.out.persistence.adapter;

import com.starmix.checkmate.adapter.out.persistence.entity.*;
import com.starmix.checkmate.adapter.out.persistence.mapper.FeatureMapper;
import com.starmix.checkmate.adapter.out.persistence.mongo.FeatureMongoRepository;
import com.starmix.checkmate.application.port.out.persistence.FeaturePersistencePort;
import com.starmix.checkmate.domain.feature.Feature;
import com.starmix.checkmate.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class FeaturePersistenceAdapter implements FeaturePersistencePort {

    private final FeatureMongoRepository featureMongoRepository;

    @Override
    public Optional<Feature> findById(String id) {
        try {
            Optional<FeatureEntity> featureEntity = featureMongoRepository.findById(id);
            return featureEntity.map(FeatureMapper::toDomain);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
