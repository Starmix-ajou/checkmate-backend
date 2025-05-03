package com.starmix.checkmate.adapter.out.persistence.adapter;

import com.starmix.checkmate.adapter.out.persistence.entity.DailyScrumEntity;
import com.starmix.checkmate.adapter.out.persistence.mapper.DailyScrumMapper;
import com.starmix.checkmate.adapter.out.persistence.mongo.DailyScrumMongoRepository;
import com.starmix.checkmate.application.port.out.persistence.DailyScrumPersistencePort;
import com.starmix.checkmate.domain.dailyScrum.DailyScrum;
import com.starmix.checkmate.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        try {
            List<DailyScrumEntity> dailyScrumEntities = dailyScrumMongoRepository.findAllByProjectId(projectId);
            return dailyScrumEntities.stream().map(DailyScrumMapper::toDomain).toList();
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Optional<DailyScrum> findByTimestampAndProjectId(String projectId, LocalDate timestamp) {
        try {
            Optional<DailyScrumEntity> dailyScrum = dailyScrumMongoRepository.findByProjectIdAndTimestamp(projectId, timestamp);
            return dailyScrum.map(DailyScrumMapper::toDomain);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String save(DailyScrum dailyScrum) {
        try {
            DailyScrumEntity dailyScrumEntity = DailyScrumMapper.toEntity(dailyScrum);
            return dailyScrumMongoRepository.save(dailyScrumEntity).getId();
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
