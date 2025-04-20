package com.starmix.checkmate.adapter.in.http.auth;

import com.starmix.checkmate.adapter.in.http.auth.response.UserInfoResponse;
import com.starmix.checkmate.application.service.AuthService;
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
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public ResponseEntity<UserInfoResponse> login (@AuthenticationPrincipal Jwt jwt) {
        User user = authService.authenticate(jwt);
        UserInfoResponse response = UserInfoResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
        return ResponseEntity.ok().body(response);
    }
}