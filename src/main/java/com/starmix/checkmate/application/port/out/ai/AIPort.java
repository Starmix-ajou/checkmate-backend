package com.starmix.checkmate.application.port.out.ai;

import com.starmix.checkmate.domain.project.Project;
import com.starmix.checkmate.domain.project.Suggestion;

public interface AIPort {
    Suggestion createFunctionDefinition(Project project);
}
