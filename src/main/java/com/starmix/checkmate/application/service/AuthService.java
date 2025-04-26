package com.starmix.checkmate.application.service;

import com.starmix.checkmate.adapter.out.oauth.dto.OAuthUserInfo;
import com.starmix.checkmate.application.port.out.oauth.GoogleOAuthPort;
import com.starmix.checkmate.application.port.out.persistence.UserPersistencePort;
import com.starmix.checkmate.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserPersistencePort userRepositoryPort;
    private final GoogleOAuthPort googleOAuthPort;

    public User authenticate(Jwt jwt) {
        OAuthUserInfo oAuthUserInfo = googleOAuthPort.getUserInfo(jwt);

        Optional<User> existingUser = userRepositoryPort.findByEmail(oAuthUserInfo.getEmail());

        return existingUser.orElseGet(() -> {
            User newUser = User.builder()
                    .email(oAuthUserInfo.getEmail())
                    .name(oAuthUserInfo.getName())
                    .build();
            userRepositoryPort.save(newUser);
            return newUser;
        });
    }
}