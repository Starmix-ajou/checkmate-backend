package com.starmix.checkmate.adapter.in.rest.common.response.statistics;

import lombok.Builder;

@Builder
public record DailyScrumStatistics(
        Integer totalDays,
        Integer doneDays,
        double doneRate
) {
    public static DailyScrumStatistics from(Integer doneDays, Integer totalDays) {
        double doneRate = (totalDays != null && totalDays != 0)
                ? (double) doneDays / totalDays
                : 0.0;
        return DailyScrumStatistics.builder()
                .doneDays(doneDays)
                .totalDays(totalDays)
                .doneRate(doneRate)
                .build();
    }
}
