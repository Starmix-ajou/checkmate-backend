package com.starmix.checkmate.adapter.in.rest.web.auth.response;

import lombok.Builder;

@Builder
public record UserInfoResponse(
        String email,
        String name,
        String accessToken
) { }
