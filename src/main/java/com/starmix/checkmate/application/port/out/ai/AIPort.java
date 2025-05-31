package com.starmix.checkmate.application.port.out.ai;

import com.starmix.checkmate.adapter.in.sse.web.project.request.FeedbackFeatureSpecificationRequest;
import com.starmix.checkmate.adapter.out.ai.client.response.CreateSprintFeignResponse;
import com.starmix.checkmate.adapter.out.ai.dto.FeedbackDto;
import com.starmix.checkmate.domain.feature.Feature;
import com.starmix.checkmate.domain.project.Project;
import com.starmix.checkmate.domain.project.Suggestion;

import java.time.LocalDate;
import java.util.List;

public interface AIPort {
    Suggestion createFunctionDefinition(Project project, String definitionUrl);
    FeedbackDto feedbackFeatureDefinition(String email, String feedback);
    List<Feature> createFeatureSpecification(String email);
    FeedbackDto feedbackFeatureSpecification(String email, FeedbackFeatureSpecificationRequest feedback);
    CreateSprintFeignResponse createSprint(String projectId, List<String> pendingTaskIds, LocalDate startDate);
}
