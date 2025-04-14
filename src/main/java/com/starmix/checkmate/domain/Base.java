package com.starmix.checkmate.domain;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@Getter
public abstract class Base {
    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
