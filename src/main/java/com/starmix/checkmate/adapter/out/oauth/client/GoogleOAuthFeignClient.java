package com.starmix.checkmate.adapter.out.oauth.client;

import com.starmix.checkmate.adapter.out.oauth.client.response.GoogleOAuthUserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "googleOAuthFeignClient", url = "https://www.googleapis.com")
public interface GoogleOAuthFeignClient {
    @GetMapping("/oauth2/v3/userinfo")
    GoogleOAuthUserInfoResponse getUserInfo(@RequestHeader("Authorization") String accessToken);
}