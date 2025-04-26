package com.starmix.checkmate.application.port.out.oauth;

import com.starmix.checkmate.adapter.out.oauth.dto.OAuthUserInfo;

public interface GoogleOAuthPort {
    OAuthUserInfo getUserInfo(String accessToken);
}
