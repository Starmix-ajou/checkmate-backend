package com.starmix.checkmate.application.port.out.persistence;

import com.starmix.checkmate.domain.feature.Feature;

import java.util.Optional;

public interface FeaturePersistencePort {
    Optional<Feature> findById(String id);
}
