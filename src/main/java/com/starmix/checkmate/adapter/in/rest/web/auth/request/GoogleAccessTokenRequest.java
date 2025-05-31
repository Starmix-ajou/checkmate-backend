package com.starmix.checkmate.adapter.in.rest.web.auth.request;

import lombok.Builder;

@Builder
public record GoogleAccessTokenRequest(
        String accessToken
) { }
