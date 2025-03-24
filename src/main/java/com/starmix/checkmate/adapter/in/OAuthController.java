package com.starmix.checkmate.adapter.in;

import com.starmix.checkmate.adapter.in.dto.oAuth.UserInfoResponse;
import com.starmix.checkmate.application.service.OAuthService;
import com.starmix.checkmate.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class OAuthController {

    private final OAuthService oAuthService;

    @GetMapping("/login")
    public ResponseEntity<UserInfoResponse> login(
            @AuthenticationPrincipal Jwt jwt
    ) {
        User user = oAuthService.authenticate(jwt);
        UserInfoResponse response = UserInfoResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
        return ResponseEntity.ok().body(response);
    }
}