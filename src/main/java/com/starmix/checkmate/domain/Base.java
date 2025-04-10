package com.starmix.checkmate.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public abstract class Base {
    private final Long id;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
