package com.starmix.checkmate.adapter.out.ai.client.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CreateActionItemsFeignResponse(
        String title,
        String description,
        String assigneeId,
        LocalDate endDate,
        String epicId
) { }