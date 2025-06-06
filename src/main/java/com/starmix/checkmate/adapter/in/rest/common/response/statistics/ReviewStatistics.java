package com.starmix.checkmate.adapter.in.rest.common.response.statistics;

import lombok.Builder;

@Builder
public record ReviewStatistics(
        Integer totalCount,
        Integer doneCount,
        double doneRate
) {
    public static ReviewStatistics from(Integer totalCount, Integer doneCount) {
        return ReviewStatistics.builder()
                .totalCount(totalCount)
                .doneCount(doneCount)
                .doneRate((double) doneCount/totalCount)
                .build();
    }
}