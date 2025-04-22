package com.starmix.checkmate.adapter.in.sse.project;

import com.starmix.checkmate.adapter.in.sse.project.request.CreateFeatureDefinitionRequest;
import com.starmix.checkmate.adapter.in.sse.project.request.CreateFeatureSpecificationRequest;
import com.starmix.checkmate.adapter.in.sse.project.request.FeedbackRequest;
import com.starmix.checkmate.adapter.in.sse.project.response.CreateFeatureDefinitionResponse;
import com.starmix.checkmate.adapter.in.sse.project.response.CreateFeatureSpecificationResponse;
import com.starmix.checkmate.adapter.in.sse.project.response.FeedbackResponse;
import com.starmix.checkmate.application.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/project")
public class ProjectSseController {

    private final ProjectService projectService;

    @PostMapping("/definition")
    public ResponseEntity<CreateFeatureDefinitionResponse> createFeatureDefinition(
            @RequestBody CreateFeatureDefinitionRequest request
    ) {
        CreateFeatureDefinitionResponse response = projectService.createFeatureDefinition(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/definition")
    public ResponseEntity<FeedbackResponse> feedbackFeatureDefinition(
            @RequestBody FeedbackRequest request
    ) {
        FeedbackResponse response = projectService.feedbackFeatureDefinition(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/specification")
    public ResponseEntity<CreateFeatureSpecificationResponse> createFeatureSpecification() {
        CreateFeatureSpecificationResponse response = projectService.createFeatureSpecification();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/specification")
    public ResponseEntity<FeedbackResponse> feedbackFeatureSpecification(
            @RequestBody FeedbackRequest request
    ) {
        FeedbackResponse response = projectService.feedbackFeatureSpecification(request);
        return ResponseEntity.ok(response);
    }
}