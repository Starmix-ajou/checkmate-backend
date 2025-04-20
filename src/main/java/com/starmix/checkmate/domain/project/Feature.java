package com.starmix.checkmate.domain.project;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Feature {
    private final String name;
    private final String useCase;
    private final String input;
    private final String output;
}
