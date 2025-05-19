package com.starmix.checkmate.adapter.in.sse.project.request;

import com.starmix.checkmate.adapter.in.common.ProfileDto;

import java.time.LocalDate;
import java.util.List;

public record CreateFeatureDefinitionRequest(
        String title,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        List<UserBrief> members,
        String definitionUrl
) {
    public record UserBrief(
            String email,
            ProfileDto profile
    ) { }
}