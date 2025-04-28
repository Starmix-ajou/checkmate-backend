package com.starmix.checkmate.domain.user;

import com.starmix.checkmate.adapter.out.oauth.dto.OAuthUserInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder(toBuilder = true)
@Getter
public class User {
    private String userId;
    private String name;
    private String email;
    private String profileImageUrl;
    private List<Profile> profiles;
    private Role role;
    private List<String> pendingProjectIds;

    public void addPendingProject(String projectId) {
        this.pendingProjectIds.add(projectId);
    }

    public void denyPendingProject(String projectId) {
        this.pendingProjectIds.remove(projectId);
    }

    public static User register(OAuthUserInfo oAuthUserInfo) {
        return User.builder()
                .email(oAuthUserInfo.email())
                .name(oAuthUserInfo.name())
                .profileImageUrl(oAuthUserInfo.profileImage())
                .profiles(new ArrayList<>())
                .role(Role.DEVELOPER)
                .pendingProjectIds(new ArrayList<>())
                .build();
    }
}