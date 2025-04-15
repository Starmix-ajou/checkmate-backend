package com.starmix.checkmate.application.service;

import com.starmix.checkmate.application.port.out.persistence.DailyScrumPersistencePort;
import com.starmix.checkmate.domain.dailyScrum.DailyScrum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DailyScrumService {

    private final DailyScrumPersistencePort dailyScrumPersistencePort;

    public List<DailyScrum> getDailyScrumsByProjectId(String projectId) {
        return dailyScrumPersistencePort.findAllByProjectId(projectId);
    }

    public DailyScrum getTodayDailyScrum() {
        Optional<DailyScrum> dailyScrum = dailyScrumPersistencePort.findByTimestamp(LocalDate.now());
        return dailyScrum.orElse(null);
    }
}