package com.starmix.checkmate.adapter.in.rest.web.auth.response;

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
