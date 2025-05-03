package com.starmix.checkmate.adapter.out.ai.client.response;

import com.starmix.checkmate.domain.project.Feature;
import com.starmix.checkmate.domain.project.Suggestion;
import lombok.Builder;

import java.util.List;

@Builder
public record CreateFeatureDefinitionFeignResponse(
        FeatureDefinitionSuggestion suggestion
) {
    public record FeatureDefinitionSuggestion(
            List<String> features,
            List<Suggestion.Topic> suggestions
    ) {
        public Suggestion toDomain() {
            List<Feature> features = this.features.stream()
                    .map(Feature::fromFeatureName).toList();

            return Suggestion.builder()
                    .features(features)
                    .suggestions(this.suggestions)
                    .build();
        }
    }
}