package com.starmix.checkmate.domain.epic;

import com.starmix.checkmate.domain.feature.Feature;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
public class Epic {
    private String epicId;
    private String title;
    private String description;
    private String projectId;
    private String featureId;
    private LocalDate startDate;
    private LocalDate endDate;

    public static Epic create(
            String title, String description, String projectId
    ) {
        String epicId = UUID.randomUUID().toString();

        return Epic.builder()
                .epicId(epicId)
                .title(title)
                .description(description)
                .projectId(projectId)
                .featureId(null)
                .build();
    }

    public static Epic fromFeature(Feature feature, String projectId) {
        String epicId = UUID.randomUUID().toString();

        return Epic.builder()
                .epicId(epicId)
                .title(feature.getName())
                .description(feature.getUseCase())
                .projectId(projectId)
                .featureId(feature.getFeatureId())
                .build();
    }

    public void updateDates(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}