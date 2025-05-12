package com.starmix.checkmate.adapter.out.redis.dto;

import com.starmix.checkmate.domain.epic.Epic;
import com.starmix.checkmate.domain.sprint.Sprint;
import lombok.Builder;

import java.util.List;

@Builder
public record SprintDetail(
        Sprint sprint,
        List<Epic> epics
) { }
