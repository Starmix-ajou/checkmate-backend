package com.starmix.checkmate.adapter.out.slack.log;

import lombok.Getter;

@Getter
public enum SlackLabel {
    SYSTEM_ALERT(":rotating_light:"),
    SECURITY(":rotating_light:"),
    PERFORMANCE(":rotating_light:"),
    RESERVATION(":pencil:"),
    APPROVE(":white_check_mark:"),
    DENY(":x:"),
    OTHER(":pencil:");

    private final String icon;

    SlackLabel(String icon) {
        this.icon = icon;
    }
}
