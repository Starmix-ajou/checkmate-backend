package com.starmix.checkmate.infrastructure.config;

import lombok.Getter;

@Getter
public enum EnvStep {
    PROD("prod"),
    DEV("dev"),
    LOCAL("local");

    private final String displayName;

    EnvStep(String displayName) {
        this.displayName = displayName;
    }
}
