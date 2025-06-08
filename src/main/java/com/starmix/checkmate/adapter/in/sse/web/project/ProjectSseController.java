package com.starmix.checkmate.adapter.in.sse.web.project;

import com.starmix.checkmate.adapter.in.sse.common.SseEmitterManager;
import com.starmix.checkmate.adapter.in.sse.common.SseType;
import com.starmix.checkmate.adapter.in.sse.web.project.request.CreateFeatureDefinitionRequest;
import com.starmix.checkmate.adapter.in.sse.web.project.request.FeedbackFeatureSpecificationRequest;
import com.starmix.checkmate.adapter.in.sse.web.project.request.FeedbackRequest;
import com.starmix.checkmate.adapter.in.sse.web.project.response.CreateFeatureDefinitionResponse;
import com.starmix.checkmate.adapter.in.sse.web.project.response.CreateFeatureSpecificationResponse;
import com.starmix.checkmate.adapter.in.sse.web.project.response.FeedbackResponse;
import com.starmix.checkmate.application.service.ProjectService;
import com.starmix.checkmate.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sse/project")
public class ProjectSseController {

    private final ProjectService projectService;
    private final SseEmitterManager sseEmitterManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/definition")
    public void createFeatureDefinition(@RequestBody CreateFeatureDefinitionRequest request) {
        String userId = jwtUtil.extractUser().getUserId();
        CreateFeatureDefinitionResponse response = projectService.createFeatureDefinition(request);
        sseEmitterManager.sendEventTo(SseType.PROJECT_SPRINT, userId, "create-feature-definition", response);
    }

    @PutMapping("/definition")
    public void feedbackFeatureDefinition(@RequestBody FeedbackRequest request) {
        String userId = jwtUtil.extractUser().getUserId();
        FeedbackResponse response = projectService.feedbackFeatureDefinition(request);
        sseEmitterManager.sendEventTo(SseType.PROJECT_SPRINT, userId, "feedback-feature-definition", response);
    }

    @GetMapping("/specification")
    public void createFeatureSpecification() {
        String userId = jwtUtil.extractUser().getUserId();
        CreateFeatureSpecificationResponse response = projectService.createFeatureSpecification();
        sseEmitterManager.sendEventTo(SseType.PROJECT_SPRINT, userId, "create-feature-specification", response);
    }

    @PutMapping("/specification")
    public void feedbackFeatureSpecification(@RequestBody FeedbackFeatureSpecificationRequest request) {
        String userId = jwtUtil.extractUser().getUserId();
        FeedbackResponse response = projectService.feedbackFeatureSpecification(request);
        sseEmitterManager.sendEventTo(SseType.PROJECT_SPRINT, userId, "feedback-feature-specification", response);
    }
}
