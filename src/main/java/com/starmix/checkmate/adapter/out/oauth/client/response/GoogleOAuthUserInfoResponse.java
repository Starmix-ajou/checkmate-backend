package com.starmix.checkmate.adapter.out.oauth.client.response;

import lombok.Getter;

@Getter
public class GoogleOAuthUserInfoResponse {
    private String sub;
    private String name;
    private String given_name;
    private String family_name;
    private String picture;
    private String email;
    private boolean email_verified;
    private String locale;
}