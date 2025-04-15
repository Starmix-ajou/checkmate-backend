package com.starmix.checkmate.domain.epic;

import com.starmix.checkmate.domain.Base;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class Epic extends Base {
    private final String title;
    private final String description;
    private final String projectId;
}