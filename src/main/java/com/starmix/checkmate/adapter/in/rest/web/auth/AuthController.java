package com.starmix.checkmate.adapter.in.rest.web.auth;

import com.starmix.checkmate.adapter.in.rest.web.auth.request.GoogleAccessTokenRequest;
import com.starmix.checkmate.adapter.in.rest.web.auth.response.GetIsLeaderResponse;
import com.starmix.checkmate.adapter.in.rest.web.auth.response.UserInfoResponse;
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
    public ResponseEntity<UserInfoResponse> login(@RequestBody GoogleAccessTokenRequest request) {
        UserInfoResponse response = authService.authenticate(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/is-leader")
    public ResponseEntity<GetIsLeaderResponse> getIsLeader(@RequestParam String projectId) {
        GetIsLeaderResponse response = authService.getIsLeader(projectId);
        return ResponseEntity.ok().body(response);
    }
}