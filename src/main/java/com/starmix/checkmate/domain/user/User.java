package com.starmix.checkmate.domain.user;

import com.starmix.checkmate.adapter.out.oauth.dto.OAuthUserInfo;
import com.starmix.checkmate.global.exception.CustomException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

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

    public void addProfile(Profile profile) {
        boolean exists = profiles.stream().anyMatch(
                p -> p.getProjectId().equals(profile.getProjectId())
        );
        if (!exists) {
            profiles.add(profile);
        }
    }

    public void removeProfile(Profile profile) {
        this.profiles.remove(profile);
    }

    public void approve(String projectId) {
        getProfileByProjectId(projectId).activeProfile();
    }

    public void deny(String projectId) {
        removeProfile(getProfileByProjectId(projectId));
    }

    public Profile getProfileByProjectId(String projectId) {
        return profiles.stream().filter(
                profile -> profile.getProjectId().equals(projectId)
        ).findFirst().orElseThrow(() -> new CustomException("Permission denied", HttpStatus.FORBIDDEN));
    }


    public static User register(OAuthUserInfo oAuthUserInfo) {
        return User.builder()
                .email(oAuthUserInfo.email())
                .name(oAuthUserInfo.name())
                .profileImageUrl(oAuthUserInfo.profileImage())
                .profiles(new ArrayList<>())
                .role(Role.DEVELOPER)
                .build();
    }
}