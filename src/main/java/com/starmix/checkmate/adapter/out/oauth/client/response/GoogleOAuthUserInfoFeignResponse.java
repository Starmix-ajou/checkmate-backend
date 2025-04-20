package com.starmix.checkmate.adapter.out.oauth.client.response;

import lombok.Builder;

@Builder
public record GoogleOAuthUserInfoFeignResponse(
        String sub,
        String name,
        String given_name,
        String family_name,
        String picture,
        String email,
        boolean email_verified,
        String locale
) { }