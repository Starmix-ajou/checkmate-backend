package com.starmix.checkmate.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.starmix.checkmate.adapter.in.common.ProfileDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder(toBuilder = true)
@Getter
public class Profile {
    private List<String> positions;
    private String projectId;
    private Role role;

    @JsonIgnore
    private Boolean isActive;

    public static Profile init(ProfileDto profileDto, String projectId) {
        return Profile.builder()
                .positions(profileDto.positions())
                .projectId(projectId)
                .isActive(false)
                .role(Role.DEVELOPER)
                .build();
    }

    public static Profile initProductManager(String projectId) {
        return Profile.builder()
                .positions(List.of(Role.PRODUCT_MANAGER.getDescription()))
                .projectId(projectId)
                .isActive(false)
                .role(Role.PRODUCT_MANAGER)
                .build();
    }

    public void updatePositions(List<String> positions) {
        this.positions = positions;
    }

    public void activeProfile() {
        this.isActive = true;
    }

    public void inactiveProfile() {
        this.isActive = false;
    }
}