package com.starmix.checkmate.adapter.in.sse.project;

import com.starmix.checkmate.adapter.in.sse.project.request.CreateFeatureDefinitionRequest;
import com.starmix.checkmate.adapter.in.sse.project.request.FeatureDefinitionFeedbackRequest;
import com.starmix.checkmate.adapter.in.sse.project.response.CreateFeatureDefinitionResponse;
import com.starmix.checkmate.adapter.in.sse.project.response.FeatureDefinitionFeedbackResponse;
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

    @PostMapping("/definition-feedback")
    public ResponseEntity<FeatureDefinitionFeedbackResponse> featureDefinitionFeedback(@RequestBody FeatureDefinitionFeedbackRequest request) {
        FeatureDefinitionFeedbackResponse response = projectService.featureDefinitionFeedback(request);
        return ResponseEntity.ok(response);
    }
//
//    @PostMapping("/specification")
//    public ResponseEntity<Project> createFeatureSpecification(@RequestBody CreateFeatureSpecificationRequest request) {
//
//    }
}