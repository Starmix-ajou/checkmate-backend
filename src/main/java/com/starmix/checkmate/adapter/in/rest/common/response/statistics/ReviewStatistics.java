package com.starmix.checkmate.adapter.in.rest.common.response.statistics;

import lombok.Builder;

@Builder
public record ReviewStatistics(
        Integer totalCount,
        Integer doneCount,
        double doneRate
) {
    public static ReviewStatistics from(Integer totalCount, Integer doneCount) {
        double doneRate = (totalCount != null && totalCount != 0)
                ? (double) doneCount / totalCount
                : 0.0;
        return ReviewStatistics.builder()
                .totalCount(totalCount)
                .doneCount(doneCount)
                .doneRate(doneRate)
                .build();
    }
}