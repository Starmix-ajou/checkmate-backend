package com.starmix.checkmate.adapter.in.sse.project;

import com.starmix.checkmate.adapter.in.sse.common.SseEmitterManager;
import com.starmix.checkmate.adapter.in.sse.project.request.CreateFeatureDefinitionRequest;
import com.starmix.checkmate.adapter.in.sse.project.request.FeedbackFeatureSpecificationRequest;
import com.starmix.checkmate.adapter.in.sse.project.request.FeedbackRequest;
import com.starmix.checkmate.adapter.in.sse.project.response.CreateFeatureDefinitionResponse;
import com.starmix.checkmate.adapter.in.sse.project.response.CreateFeatureSpecificationResponse;
import com.starmix.checkmate.adapter.in.sse.project.response.FeedbackResponse;
import com.starmix.checkmate.application.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/project")
public class ProjectSseController {

    private final ProjectService projectService;
    private final SseEmitterManager sseEmitterManager;

    @PostMapping("/definition")
    public void createFeatureDefinition(@RequestBody CreateFeatureDefinitionRequest request) {
        CreateFeatureDefinitionResponse response = projectService.createFeatureDefinition(request);
        sseEmitterManager.sendEvent("create-feature-definition", response);
    }

    @PutMapping("/definition")
    public void feedbackFeatureDefinition(@RequestBody FeedbackRequest request) {
        FeedbackResponse response = projectService.feedbackFeatureDefinition(request);
        sseEmitterManager.sendEvent("feedback-feature-definition", response);
    }

    @GetMapping("/specification")
    public void createFeatureSpecification() {
        CreateFeatureSpecificationResponse response = projectService.createFeatureSpecification();
        sseEmitterManager.sendEvent("create-feature-specification", response);
    }

    @PutMapping("/specification")
    public void feedbackFeatureSpecification(@RequestBody FeedbackFeatureSpecificationRequest request) {
        FeedbackResponse response = projectService.feedbackFeatureSpecification(request);
        sseEmitterManager.sendEvent("feedback-feature-specification", response);
    }
}
