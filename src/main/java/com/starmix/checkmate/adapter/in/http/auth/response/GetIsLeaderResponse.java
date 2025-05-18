package com.starmix.checkmate.adapter.in.http.auth.response;

import lombok.Builder;

@Builder
public record GetIsLeaderResponse(
        Boolean isLeader
) {
    public static GetIsLeaderResponse fromIsLeader(Boolean isLeader) {
        return GetIsLeaderResponse.builder()
                .isLeader(isLeader)
                .build();
    }
}
