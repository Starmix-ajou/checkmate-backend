package com.starmix.checkmate.adapter.in.http.auth.response;

import lombok.Builder;

@Builder
public record UserInfoResponse(
        String email,
        String name
) { }
