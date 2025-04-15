package com.starmix.checkmate.domain.sprint;

import com.starmix.checkmate.domain.Base;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@Getter
public class Sprint extends Base {
    private final String title;
    private final String description;
    private final Integer sequence;
    private final String projectId;
    private final LocalDate startDate;
    private final LocalDate endDate;
}