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

    @JsonIgnore
    private Boolean isActive;

    public static Profile init(ProfileDto profileDto, String projectId) {
        return Profile.builder()
                .positions(profileDto.positions())
                .projectId(projectId)
                .isActive(false)
                .build();
    }

    public void activeProfile() {
        this.isActive = true;
    }

    public void inactiveProfile() {
        this.isActive = false;
    }
}