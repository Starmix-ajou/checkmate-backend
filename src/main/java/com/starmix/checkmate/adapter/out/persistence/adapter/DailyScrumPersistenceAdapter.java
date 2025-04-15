package com.starmix.checkmate.adapter.out.persistence.adapter;

import com.starmix.checkmate.adapter.out.persistence.entity.DailyScrumEntity;
import com.starmix.checkmate.adapter.out.persistence.mapper.DailyScrumMapper;
import com.starmix.checkmate.adapter.out.persistence.mongo.DailyScrumMongoRepository;
import com.starmix.checkmate.application.port.out.persistence.DailyScrumPersistencePort;
import com.starmix.checkmate.domain.dailyScrum.DailyScrum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class DailyScrumPersistenceAdapter implements DailyScrumPersistencePort {

    private final DailyScrumMongoRepository dailyScrumMongoRepository;

    @Override
    public List<DailyScrum> findAllByProjectId(String projectId) {
        List<DailyScrumEntity> dailyScrumEntities = dailyScrumMongoRepository.findAllByProjectId(projectId);
        return dailyScrumEntities.stream().map(DailyScrumMapper::toDomain).toList();
    }

    @Override
    public Optional<DailyScrum> findByTimestamp(LocalDate timestamp) {
        Optional<DailyScrumEntity> dailyScrum = dailyScrumMongoRepository.findByTimestamp(timestamp);
        return dailyScrum.map(DailyScrumMapper::toDomain);
    }
}
