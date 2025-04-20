package com.starmix.checkmate.adapter.in.http.sprint;

import com.starmix.checkmate.application.service.SprintService;
import com.starmix.checkmate.domain.sprint.Sprint;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sprint")
public class SprintController {

    private final SprintService sprintService;

    @GetMapping
    public ResponseEntity<List<Sprint>> getSprintsByProjectId (
            @RequestParam String projectId
    ) {
        List<Sprint> sprints = sprintService.getSprintsByProjectId(projectId);
        return ResponseEntity.ok().body(sprints);
    }
}