package com.starmix.checkmate.domain.feature;

import com.starmix.checkmate.domain.common.Stack;
import com.starmix.checkmate.domain.task.Priority;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder(toBuilder = true)
public class Feature {
    private final String featureId;
    private final String name;
    private final String useCase;
    private final String input;
    private final String output;
    private final String preCondition;
    private final String postCondition;
    private final List<Stack> stacks;
    private final Integer expectedDays;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Integer difficulty;
    private final Priority priority;
    private final String projectId;

    public static Feature fromFeatureName(String featureName) {
        return Feature.builder()
                .name(featureName)
                .build();
    }
}
