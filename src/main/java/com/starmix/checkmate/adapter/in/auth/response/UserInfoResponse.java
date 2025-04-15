package com.starmix.checkmate.adapter.in.auth.response;

import lombok.Builder;

@Builder
public record UserInfoResponse(
        String email,
        String name
) { }
