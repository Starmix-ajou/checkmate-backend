package com.starmix.checkmate.adapter.in.sse.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SseType {
    PROJECT_SPRINT("projectsprint-"),
    NOTIFICATION("notification-");

    private final String prefix;
}
