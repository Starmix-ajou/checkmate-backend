package com.starmix.checkmate.application.port.out.ai;

import com.starmix.checkmate.adapter.out.ai.client.response.FeedbackFeignResponse;
import com.starmix.checkmate.domain.project.Feature;
import com.starmix.checkmate.domain.project.Project;
import com.starmix.checkmate.domain.project.Suggestion;

import java.util.List;

public interface AIPort {
    Suggestion createFunctionDefinition(Project project, String definitionUrl);
    FeedbackFeignResponse feedbackFeatureDefinition(String email, String feedback);
    List<Feature> createFeatureSpecification(String email);
    FeedbackFeignResponse feedbackFeatureSpecification(String email, String feedback);
}
