package com.starmix.checkmate.domain.feature;

import java.time.LocalDate;

public record Time(
        Integer expectedDays,
        LocalDate startDate,
        LocalDate endDate
) {
}
