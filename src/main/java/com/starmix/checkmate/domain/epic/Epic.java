package com.starmix.checkmate.domain.epic;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class Epic {
    private String epicId;
    private String title;
    private String description;
    private String projectId;
}