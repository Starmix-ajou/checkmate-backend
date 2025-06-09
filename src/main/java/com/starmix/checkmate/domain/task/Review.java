package com.starmix.checkmate.domain.task;

import lombok.Builder;

@Builder
public record Review(
        String learn,
        String hardest,
        String next
) { }
