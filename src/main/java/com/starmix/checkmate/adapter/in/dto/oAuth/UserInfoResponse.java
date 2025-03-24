package com.starmix.checkmate.adapter.in.dto.oAuth;

import lombok.Builder;

@Builder
public record UserInfoResponse(
        String email,
        String name
) { }
