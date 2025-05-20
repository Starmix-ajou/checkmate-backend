package com.starmix.checkmate.domain.epic;

import com.starmix.checkmate.domain.feature.Feature;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder(toBuilder = true)
@Getter
public class Epic {
    private String epicId;
    private String title;
    private String description;
    private String projectId;

    public static Epic create(
            String title, String description, String projectId
    ) {
        String epicId = UUID.randomUUID().toString();

        return Epic.builder()
                .epicId(epicId)
                .title(title)
                .description(description)
                .projectId(projectId)
                .build();
    }

    public static Epic fromFeature(Feature feature, String projectId) {
        String epicId = UUID.randomUUID().toString();

        return Epic.builder()
                .epicId(epicId)
                .title(feature.getName())
                .description(feature.getUseCase())
                .projectId(projectId)
                .build();
    }
}