package com.starmix.checkmate.adapter.in.sse.sprint;

import com.starmix.checkmate.adapter.in.sse.common.SseEmitterManager;
import com.starmix.checkmate.adapter.in.sse.sprint.request.CreateSprintRequest;
import com.starmix.checkmate.adapter.in.sse.sprint.request.UpdateSprintRequest;
import com.starmix.checkmate.adapter.in.sse.sprint.response.UpdateSprintResponse;
import com.starmix.checkmate.application.service.SprintService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sprint")
public class SprintSseController {

    private final SprintService sprintService;
    private final SseEmitterManager sseEmitterManager;

    @PostMapping
    public void createSprint(@RequestParam String projectId, @RequestBody CreateSprintRequest request) {
        List<UpdateSprintResponse> response = sprintService.createSprint(projectId, request);
        sseEmitterManager.sendEvent("create-sprint", response);
    }

    @PutMapping
    public void updateSprint(@RequestParam String projectId, @RequestBody List<UpdateSprintRequest> request) {
        List<UpdateSprintResponse> response = sprintService.updateSprint(projectId, request);
        sseEmitterManager.sendEvent("feedback-sprint", response);
    }
}
