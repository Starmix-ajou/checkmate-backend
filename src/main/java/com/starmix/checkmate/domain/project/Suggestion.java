package com.starmix.checkmate.domain.project;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Suggestion {
    private final List<Feature> features;
    private final List<Topic> suggestions;

    public record Topic(
            String question,
            List<String> answers
    ) { }
}
