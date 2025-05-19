package com.starmix.checkmate.application.service;

import com.starmix.checkmate.adapter.in.http.auth.request.GoogleAccessTokenRequest;
import com.starmix.checkmate.adapter.in.http.auth.response.GetIsLeaderResponse;
import com.starmix.checkmate.adapter.in.http.auth.response.UserInfoResponse;
import com.starmix.checkmate.adapter.out.oauth.dto.OAuthUserInfo;
import com.starmix.checkmate.application.port.out.oauth.GoogleOAuthPort;
import com.starmix.checkmate.application.port.out.persistence.ProjectPersistencePort;
import com.starmix.checkmate.application.port.out.persistence.UserPersistencePort;
import com.starmix.checkmate.domain.project.Project;
import com.starmix.checkmate.domain.user.Role;
import com.starmix.checkmate.domain.user.User;
import com.starmix.checkmate.global.exception.CustomException;
import com.starmix.checkmate.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserPersistencePort userRepositoryPort;
    private final GoogleOAuthPort googleOAuthPort;
    private final JwtUtil jwtUtil;
    private final ProjectPersistencePort projectPersistencePort;

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

    public GetIsLeaderResponse getIsLeader(String projectId) {
        User user = jwtUtil.extractUser();
        Project project = projectPersistencePort.findById(projectId)
                .orElseThrow(() -> new CustomException("Project not found", HttpStatus.NOT_FOUND));
        return GetIsLeaderResponse.fromIsLeader(project.isLeader(user));
    }
}