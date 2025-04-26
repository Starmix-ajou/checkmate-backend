package com.starmix.checkmate.adapter.in.http.auth.request;

import lombok.Builder;

@Builder
public record GoogleAccessTokenRequest(
        String accessToken
) { }
