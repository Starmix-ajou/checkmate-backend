package com.starmix.checkmate.domain.feature;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.starmix.checkmate.domain.task.Priority;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder(toBuilder = true)
public class Feature {
    private final String featureId;
    private final String name;
    private final String useCase;
    private final String input;
    private final String output;
    @JsonIgnore
    private final String preCondition;
    @JsonIgnore
    private final String postCondition;
    @JsonIgnore
    private final Integer expectedDays;
    @JsonIgnore
    private final LocalDate startDate;
    @JsonIgnore
    private final LocalDate endDate;
    @JsonIgnore
    private final Integer difficulty;
    @JsonIgnore
    private final Priority priority;
    @JsonIgnore
    private final String projectId;

    public static Feature fromFeatureName(String featureName) {
        return Feature.builder()
                .name(featureName)
                .build();
    }
}
