package com.starmix.checkmate.domain.user;

import com.starmix.checkmate.adapter.out.oauth.dto.OAuthUserInfo;
import com.starmix.checkmate.global.exception.CustomException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder(toBuilder = true)
@Getter
public class User {
    private String userId;
    private String name;
    private String email;
    private String profileImageUrl;
    private List<Profile> profiles;

    public void addProfile(Profile profile) {
        if(this.profiles == null || this.profiles.isEmpty()) {
            this.profiles = new ArrayList<>();
        }

        boolean exists = profiles.stream().anyMatch(
                p -> p.getProjectId().equals(profile.getProjectId())
        );
        if (!exists) {
            profiles.add(profile);
        }
    }

    public void approve(String projectId) {
        getProfileByProjectId(projectId).activeProfile();
    }

    public void deny(String projectId) {
        this.profiles.remove(getProfileByProjectId(projectId));
    }

    public static User register(OAuthUserInfo oAuthUserInfo) {
        return User.builder()
                .email(oAuthUserInfo.email())
                .name(oAuthUserInfo.name())
                .profileImageUrl(oAuthUserInfo.profileImage())
                .profiles(new ArrayList<>())
                .build();
    }

    public Profile getProfileByProjectId(String projectId) {
        return profiles.stream()
                .filter(profile -> profile.getProjectId().equals(projectId))
                .findFirst()
                .orElseThrow(() -> new CustomException("Profile not found", HttpStatus.NOT_FOUND));
    }

    public List<Profile> getProfilesByRole(Role role) {
        return profiles.stream().filter(
                profile -> profile.getRole().equals(role)
        ).toList();
    }

    public void deleteProfileByProjectId(String projectId) {
        profiles.removeIf(profile -> profile.getProjectId().equals(projectId));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, email);
    }
}