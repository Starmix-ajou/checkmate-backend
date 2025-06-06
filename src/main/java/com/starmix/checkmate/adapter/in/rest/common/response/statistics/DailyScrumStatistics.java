package com.starmix.checkmate.adapter.in.rest.common.response.statistics;

import lombok.Builder;

@Builder
public record DailyScrumStatistics(
        Integer totalDays,
        Integer doneDays,
        double doneRate
) {
    public static DailyScrumStatistics from(Integer doneDays, Integer totalDays) {
        return DailyScrumStatistics.builder()
                .doneDays(doneDays)
                .totalDays(totalDays)
                .doneRate((double) doneDays/totalDays)
                .build();
    }
}
