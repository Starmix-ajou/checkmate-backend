package com.starmix.checkmate.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
public class Sprint extends Base {
    private final String name;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final List<String> epicIds;
    private final String status;
}