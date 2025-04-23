package com.starmix.checkmate.adapter.in.sse.project.request;

import com.starmix.checkmate.domain.user.User;

import java.time.LocalDate;
import java.util.List;

public record CreateFeatureDefinitionRequest(
        String title,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        List<User> members,
        String definitionUrl
) { }
