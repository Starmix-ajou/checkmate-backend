package com.starmix.checkmate.domain.user;

import com.starmix.checkmate.adapter.in.common.ProfileDto;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Builder(toBuilder = true)
@Getter
public class Profile {
    private List<String> positions;
    private String projectId;
    private Role role;
    private Boolean isActive;

    public static Profile init(ProfileDto profileDto, String projectId) {
        return Profile.builder()
                .positions(profileDto.positions())
                .projectId(projectId)
                .role(Role.DEVELOPER)
                .isActive(false)
                .build();
    }

    public static Profile initProductManager(String projectId) {
        return Profile.builder()
                .positions(List.of(Role.PRODUCT_MANAGER.getDescription()))
                .projectId(projectId)
                .role(Role.PRODUCT_MANAGER)
                .isActive(false)
                .build();
    }

    public void updatePositions(List<String> positions) {
        this.positions = positions;
    }

    public void activeProfile() {
        this.isActive = true;
    }

    public static List<String> filterProjectIds(List<Profile> profiles, Predicate<Profile> predicate) {
        if (profiles == null || predicate == null) return Collections.emptyList();

        return profiles.stream()
                .filter(predicate)
                .map(Profile::getProjectId)
                .filter(Objects::nonNull)
                .toList();
    }
}
