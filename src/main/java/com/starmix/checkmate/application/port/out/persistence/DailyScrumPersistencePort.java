package com.starmix.checkmate.application.port.out.persistence;

import com.starmix.checkmate.domain.dailyScrum.DailyScrum;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyScrumPersistencePort {
    List<DailyScrum> findAllByProjectId(String projectId);
    Optional<DailyScrum> findByTimestamp(LocalDate timestamp);
}
