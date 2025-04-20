package com.starmix.checkmate.adapter.in.http.dailyScrum;

import com.starmix.checkmate.application.service.DailyScrumService;
import com.starmix.checkmate.domain.dailyScrum.DailyScrum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/daily-scrum")
public class DailyScrumController {

    private final DailyScrumService dailyScrumService;

    @GetMapping
    public ResponseEntity<List<DailyScrum>> getDailyScrumsByProjectId (
            @RequestParam String projectId
    ) {
        List<DailyScrum> tasks = dailyScrumService.getDailyScrumsByProjectId(projectId);
        return ResponseEntity.ok().body(tasks);
    }

    @GetMapping("/today")
    public ResponseEntity<DailyScrum> getTodayDailyScrum () {
        DailyScrum dailyScrum = dailyScrumService.getTodayDailyScrum();
        return ResponseEntity.ok().body(dailyScrum);
    }
}