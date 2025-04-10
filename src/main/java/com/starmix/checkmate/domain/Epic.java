package com.starmix.checkmate.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class Epic extends Base {
    private final String name;
    private final String description;
    private final List<String> taskIds;
}