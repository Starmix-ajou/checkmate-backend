package com.starmix.checkmate.application.port.out.ai;

import com.starmix.checkmate.domain.project.Feature;
import com.starmix.checkmate.domain.project.Project;
import com.starmix.checkmate.domain.project.Suggestion;

import java.util.List;

public interface AIPort {
    Suggestion createFunctionDefinition(Project project);
    List<Feature> featureDefinitionFeedback(Project project);
}
