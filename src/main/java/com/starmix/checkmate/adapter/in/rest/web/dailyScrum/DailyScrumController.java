package com.starmix.checkmate.adapter.in.rest.web.dailyScrum;

import com.starmix.checkmate.adapter.in.rest.web.dailyScrum.request.UpdateDailyScrumRequest;
import com.starmix.checkmate.application.service.DailyScrumService;
import com.starmix.checkmate.domain.dailyScrum.DailyScrum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<DailyScrum> getTodayDailyScrum (
            @RequestParam String projectId
    ) {
        DailyScrum dailyScrum = dailyScrumService.getTodayDailyScrum(projectId);
        return ResponseEntity.ok().body(dailyScrum);
    }

    @PostMapping
    public ResponseEntity<DailyScrum> createDailyScrum (
            @RequestParam String projectId,
            @RequestBody UpdateDailyScrumRequest request
    ) {
        DailyScrum dailyScrum = dailyScrumService.createDailyScrum(projectId, request);
        return ResponseEntity.ok().body(dailyScrum);
    }
}