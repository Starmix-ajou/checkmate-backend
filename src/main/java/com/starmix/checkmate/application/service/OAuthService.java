package com.starmix.checkmate.application.service;

import com.starmix.checkmate.application.port.out.UserRepositoryPort;
import com.starmix.checkmate.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OAuthService {

    private final UserRepositoryPort userRepositoryPort;

    public User authenticate(Jwt jwt) {

        String email = jwt.getClaimAsString("email");
        String name = jwt.getClaimAsString("name");

//      TODO: CustomException 구성

        Optional<User> existingUser = userRepositoryPort.findByEmail(email);

        return existingUser.orElseGet(() -> {
            User newUser = User.builder()
                    .email(email)
                    .name(name)
                    .build();
            return userRepositoryPort.save(newUser);
        });
    }
}