package com.starmix.checkmate.adapter.in.sse.web.sprint;

import com.starmix.checkmate.adapter.in.sse.common.SseEmitterManager;
import com.starmix.checkmate.adapter.in.sse.common.SseType;
import com.starmix.checkmate.adapter.in.sse.web.sprint.request.CreateSprintRequest;
import com.starmix.checkmate.adapter.in.sse.web.sprint.request.UpdateSprintRequest;
import com.starmix.checkmate.adapter.in.sse.web.sprint.response.UpdateSprintResponse;
import com.starmix.checkmate.application.service.SprintService;
import com.starmix.checkmate.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sse/sprint")
public class SprintSseController {

    private final SprintService sprintService;
    private final SseEmitterManager sseEmitterManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public void createSprint(@RequestParam String projectId, @RequestBody CreateSprintRequest request) {
        String userId = jwtUtil.extractUser().getUserId();
        List<UpdateSprintResponse> response = sprintService.createSprint(projectId, request);
        sseEmitterManager.sendEventTo(SseType.PROJECT_SPRINT, userId, "create-sprint", response);
    }

    @PutMapping
    public void updateSprint(@RequestParam String projectId, @RequestBody List<UpdateSprintRequest> request) {
        String userId = jwtUtil.extractUser().getUserId();
        List<UpdateSprintResponse> response = sprintService.updateSprint(projectId, request);
        sseEmitterManager.sendEventTo(SseType.PROJECT_SPRINT, userId, "feedback-sprint", response);
    }
}
