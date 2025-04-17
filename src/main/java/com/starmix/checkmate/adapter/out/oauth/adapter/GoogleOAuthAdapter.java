package com.starmix.checkmate.adapter.out.oauth.adapter;

import com.starmix.checkmate.adapter.out.oauth.client.GoogleOAuthFeignClient;
import com.starmix.checkmate.adapter.out.oauth.client.response.GoogleOAuthUserInfoResponse;
import com.starmix.checkmate.adapter.out.oauth.dto.OAuthUserInfo;
import com.starmix.checkmate.application.port.out.oauth.GoogleOAuthPort;
import com.starmix.checkmate.global.exception.CustomException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GoogleOAuthAdapter implements GoogleOAuthPort {

    private final GoogleOAuthFeignClient googleOAuthFeignClient;

    @Override
    public OAuthUserInfo getUserInfo(Jwt jwt) {
        try {
            GoogleOAuthUserInfoResponse response = googleOAuthFeignClient.getUserInfo("Bearer " + jwt.getTokenValue());
            return OAuthUserInfo.builder()
                    .email(response.getEmail())
                    .name(response.getName())
                    .build();
        } catch (FeignException e) {
            throw new CustomException("유효하지 않은 토큰입니다.");
        }
    }
}
