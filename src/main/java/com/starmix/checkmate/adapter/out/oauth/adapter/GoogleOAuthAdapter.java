package com.starmix.checkmate.adapter.out.oauth.adapter;

import com.starmix.checkmate.adapter.out.oauth.client.GoogleOAuthFeignClient;
import com.starmix.checkmate.adapter.out.oauth.client.response.GoogleOAuthUserInfoFeignResponse;
import com.starmix.checkmate.adapter.out.oauth.dto.OAuthUserInfo;
import com.starmix.checkmate.application.port.out.oauth.GoogleOAuthPort;
import com.starmix.checkmate.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GoogleOAuthAdapter implements GoogleOAuthPort {

    private final GoogleOAuthFeignClient googleOAuthFeignClient;

    @Override
    public OAuthUserInfo getUserInfo(String accessToken) {
        try {
            GoogleOAuthUserInfoFeignResponse response = googleOAuthFeignClient.getUserInfo("Bearer " + accessToken);
            return OAuthUserInfo.builder()
                    .email(response.email())
                    .name(response.name())
                    .profileImage(response.picture())
                    .build();
        } catch (Exception e) {
            throw new CustomException("유효하지 않은 토큰입니다.", HttpStatus.FORBIDDEN);
        }
    }
}
