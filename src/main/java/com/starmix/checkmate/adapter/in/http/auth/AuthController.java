package com.starmix.checkmate.adapter.in.http.auth;

import com.starmix.checkmate.adapter.in.http.auth.request.GoogleAccessTokenRequest;
import com.starmix.checkmate.adapter.in.http.auth.response.UserInfoResponse;
import com.starmix.checkmate.application.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserInfoResponse> login (@RequestBody GoogleAccessTokenRequest request) {
        UserInfoResponse response = authService.authenticate(request);
        return ResponseEntity.ok().body(response);
    }
}