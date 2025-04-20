package com.starmix.checkmate.domain.project;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Suggestion {
    private final List<String> features;
    private final List<Topic> topics;

    public record Topic(
            String question,
            List<String> answers
    ) { }
}
