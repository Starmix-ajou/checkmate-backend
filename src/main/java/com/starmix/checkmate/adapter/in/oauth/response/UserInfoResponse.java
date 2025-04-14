package com.starmix.checkmate.adapter.in.oauth.response;

import lombok.Builder;

@Builder
public record UserInfoResponse(
        String email,
        String name
) { }
