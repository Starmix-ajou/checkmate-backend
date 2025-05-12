package com.starmix.checkmate.domain.epic;

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
    private String sprintId;

    public static Epic create(
            String title, String description, String projectId, String sprintId
    ) {
        String epicId = UUID.randomUUID().toString();

        return Epic.builder()
                .epicId(epicId)
                .title(title)
                .description(description)
                .projectId(projectId)
                .sprintId(sprintId)
                .build();
    }
}