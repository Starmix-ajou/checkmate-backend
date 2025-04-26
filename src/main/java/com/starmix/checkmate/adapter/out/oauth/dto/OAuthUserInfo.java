package com.starmix.checkmate.adapter.out.oauth.dto;

import lombok.Builder;

@Builder
public record OAuthUserInfo(
        String email,
        String name,
        String profileImage
) { }