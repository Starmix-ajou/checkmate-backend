package com.starmix.checkmate.adapter.in.sse.project.request;

import com.starmix.checkmate.adapter.in.common.UserDto;
import com.starmix.checkmate.domain.project.Project;
import com.starmix.checkmate.domain.user.Profile;
import com.starmix.checkmate.domain.user.User;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record CreateFeatureDefinitionRequest(
        String title,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        List<UserDto> members,
        String definitionUrl
) { }