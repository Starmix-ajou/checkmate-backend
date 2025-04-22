package com.starmix.checkmate.adapter.in.sse.project;

import com.starmix.checkmate.adapter.in.sse.common.SseEmitterManager;
import com.starmix.checkmate.adapter.in.sse.project.request.CreateFeatureDefinitionRequest;
import com.starmix.checkmate.adapter.in.sse.project.request.FeedbackRequest;
import com.starmix.checkmate.application.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/project")
public class ProjectSseController {

    private final ProjectService projectService;
    private final SseEmitterManager sseEmitterManager;

    @PostMapping("/definition")
    public void createFeatureDefinition(@RequestBody CreateFeatureDefinitionRequest request) throws IOException {
        var response = projectService.createFeatureDefinition(request);
        sseEmitterManager.sendEvent("create-feature-definition", response);
    }

    @PutMapping("/definition")
    public void feedbackFeatureDefinition(@RequestBody FeedbackRequest request) throws IOException {
        var response = projectService.feedbackFeatureDefinition(request);
        sseEmitterManager.sendEvent("feedback-feature-definition", response);
    }

    @GetMapping("/specification")
    public void createFeatureSpecification() throws IOException {
        var response = projectService.createFeatureSpecification();
        sseEmitterManager.sendEvent("create-feature-specification", response);
    }

    @PutMapping("/specification")
    public void feedbackFeatureSpecification(@RequestBody FeedbackRequest request) throws IOException {
        var response = projectService.feedbackFeatureSpecification(request);
        sseEmitterManager.sendEvent("feedback-feature-specification", response);
    }
}
