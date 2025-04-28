package com.starmix.checkmate.application.service;

import com.starmix.checkmate.adapter.in.http.auth.request.GoogleAccessTokenRequest;
import com.starmix.checkmate.adapter.in.http.auth.response.UserInfoResponse;
import com.starmix.checkmate.adapter.out.oauth.dto.OAuthUserInfo;
import com.starmix.checkmate.application.port.out.oauth.GoogleOAuthPort;
import com.starmix.checkmate.application.port.out.persistence.UserPersistencePort;
import com.starmix.checkmate.domain.user.User;
import com.starmix.checkmate.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserPersistencePort userRepositoryPort;
    private final GoogleOAuthPort googleOAuthPort;
    private final JwtUtil jwtUtil;

    public UserInfoResponse authenticate(GoogleAccessTokenRequest request) {
        OAuthUserInfo oAuthUserInfo = googleOAuthPort.getUserInfo(request.accessToken());
        User user = userRepositoryPort.findByEmail(oAuthUserInfo.email())
                .orElseGet(() -> {
                    User newUser = User.register(oAuthUserInfo);
                    userRepositoryPort.save(newUser);
                    return newUser;
                });

        String accessToken = jwtUtil.generateToken(user.getEmail());
        return UserInfoResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .accessToken(accessToken)
                .build();
    }
}