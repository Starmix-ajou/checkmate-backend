package com.starmix.checkmate.domain;

import com.starmix.checkmate.adapter.out.oauth.dto.OAuthUserInfo;
import com.starmix.checkmate.domain.user.Profile;
import com.starmix.checkmate.domain.user.Role;
import com.starmix.checkmate.domain.user.User;
import com.starmix.checkmate.global.exception.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserTest {
    private User user;
    private Profile profile;
    private String projectId;

    @BeforeEach
    void setUp() {
        projectId = "project-id";
        profile = Profile.builder()
                .projectId(projectId)
                .role(Role.DEVELOPER)
                .isActive(false)
                .build();

        user = User.builder()
                .userId("user-id")
                .email("test@example.com")
                .name("테스트 유저")
                .profileImageUrl("image-url")
                .profiles(new ArrayList<>())
                .build();
    }

    @Test
    @DisplayName("OAuth 정보로 사용자 등록 테스트")
    void register() {
        // given
        OAuthUserInfo oAuthUserInfo = new OAuthUserInfo(
                "test@example.com",
                "OAuth 유저",
                "profile-image-url"
        );

        // when
        User registeredUser = User.register(oAuthUserInfo);

        // then
        assertThat(registeredUser.getEmail()).isEqualTo(oAuthUserInfo.email());
        assertThat(registeredUser.getName()).isEqualTo(oAuthUserInfo.name());
        assertThat(registeredUser.getProfileImageUrl()).isEqualTo(oAuthUserInfo.profileImage());
        assertThat(registeredUser.getProfiles()).isEmpty();
    }

    @Test
    @DisplayName("프로필 추가 테스트")
    void addProfile() {
        // when
        user.addProfile(profile);

        // then
        assertThat(user.getProfiles()).hasSize(1);
        assertThat(user.getProfiles().getFirst()).isEqualTo(profile);
    }

    @Test
    @DisplayName("중복된 프로젝트 프로필 추가 방지 테스트")
    void preventDuplicateProfile() {
        // given
        user.addProfile(profile);

        Profile duplicateProfile = Profile.builder()
                .projectId(projectId)
                .role(Role.DEVELOPER)
                .build();

        // when
        user.addProfile(duplicateProfile);

        // then
        assertThat(user.getProfiles()).hasSize(1);
    }

    @Test
    @DisplayName("프로필 승인 테스트")
    void approveProfile() {
        // given
        user.addProfile(profile);

        // when
        user.approve(projectId);

        // then
        Profile approvedProfile = user.getProfileByProjectId(projectId);
        assertThat(approvedProfile.getIsActive()).isTrue();
    }

    @Test
    @DisplayName("프로필 거부 테스트")
    void denyProfile() {
        // given
        user.addProfile(profile);

        // when
        user.deny(projectId);

        // then
        assertThat(user.getProfiles()).isEmpty();
    }

    @Test
    @DisplayName("프로젝트 ID로 프로필 조회 테스트")
    void getProfileByProjectId() {
        // given
        user.addProfile(profile);

        // when
        Profile foundProfile = user.getProfileByProjectId(projectId);

        // then
        assertThat(foundProfile).isEqualTo(profile);
    }

    @Test
    @DisplayName("존재하지 않는 프로젝트 ID로 프로필 조회시 예외 발생 테스트")
    void throwExceptionWhenProfileNotFound() {
        // when & then
        assertThatThrownBy(() -> user.getProfileByProjectId("non-existing-id"))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining("Profile not found");
    }

    @Test
    @DisplayName("역할별 프로필 조회 테스트")
    void getProfilesByRole() {
        // given
        Profile developerProfile = Profile.builder()
                .projectId("project-1")
                .role(Role.DEVELOPER)
                .build();

        Profile managerProfile = Profile.builder()
                .projectId("project-2")
                .role(Role.PRODUCT_MANAGER)
                .build();

        user.addProfile(developerProfile);
        user.addProfile(managerProfile);

        // when
        var memberProfiles = user.getProfilesByRole(Role.DEVELOPER);
        var adminProfiles = user.getProfilesByRole(Role.PRODUCT_MANAGER);

        // then
        assertThat(memberProfiles).hasSize(1).contains(developerProfile);
        assertThat(adminProfiles).hasSize(1).contains(managerProfile);
    }

    @Test
    @DisplayName("프로젝트 ID로 프로필 삭제 테스트")
    void deleteProfileByProjectId() {
        // given
        user.addProfile(profile);

        // when
        user.deleteProfileByProjectId(projectId);

        // then
        assertThat(user.getProfiles()).isEmpty();
    }

    @Test
    @DisplayName("사용자 동등성 비교 테스트")
    void testEquals() {
        // given
        User sameUser = User.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .build();

        User differentUser = User.builder()
                .userId("different-id")
                .email("different@example.com")
                .build();

        // then
        assertThat(user).isEqualTo(sameUser);
        assertThat(user).isNotEqualTo(differentUser);
    }
}