package com.starmix.checkmate.adapter.out.oauth.adapter;

import com.starmix.checkmate.adapter.out.oauth.client.GoogleOAuthFeignClient;
import com.starmix.checkmate.adapter.out.oauth.client.response.GoogleOAuthUserInfoResponse;
import com.starmix.checkmate.adapter.out.oauth.dto.OAuthUserInfo;
import com.starmix.checkmate.application.port.out.oauth.GoogleOAuthPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GoogleOAuthAdapter implements GoogleOAuthPort {

    private final GoogleOAuthFeignClient googleOAuthFeignClient;

    @Override
    public OAuthUserInfo getUserInfo(Jwt jwt) {
        GoogleOAuthUserInfoResponse response = googleOAuthFeignClient.getUserInfo("Bearer " + jwt.getTokenValue());
        return OAuthUserInfo.builder()
                .email(response.getEmail())
                .name(response.getName())
                .build();
    }
}
