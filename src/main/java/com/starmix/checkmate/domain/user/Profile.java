package com.starmix.checkmate.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.starmix.checkmate.domain.common.Stack;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder(toBuilder = true)
@Getter
public class Profile {
    private List<Stack> stacks;
    private List<String> positions;
    private String projectId;

    @JsonIgnore
    private Boolean isActive;

    public void init(String projectId) {
        this.projectId = projectId;
        this.isActive = false;
    }

    public void activeProfile() {
        this.isActive = true;
    }

    public void inactiveProfile() {
        this.isActive = false;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "stacks=" + stacks +
                ", positions=" + positions +
                ", projectId='" + projectId + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}