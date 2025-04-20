package com.starmix.checkmate.adapter.out.ai.client.request;

import com.starmix.checkmate.domain.common.Stack;
import com.starmix.checkmate.domain.project.Feature;
import com.starmix.checkmate.domain.user.User;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record FeatureDefinitionFeedbackFeignRequest(
        String title,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        List<Stack>stacks,
        List<User> members,
        User leader
) { }