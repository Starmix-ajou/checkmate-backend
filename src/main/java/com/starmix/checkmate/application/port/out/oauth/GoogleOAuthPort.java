package com.starmix.checkmate.application.port.out.oauth;

import com.starmix.checkmate.adapter.out.oauth.dto.OAuthUserInfo;
import org.springframework.security.oauth2.jwt.Jwt;

public interface GoogleOAuthPort {
    OAuthUserInfo getUserInfo(Jwt jwt);
}
