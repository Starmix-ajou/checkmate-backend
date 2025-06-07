package com.starmix.checkmate.adapter.in.rest.web.notification.response;

import lombok.Builder;

@Builder
public record NotificationCountResponse(
        Integer count
) {
    public static NotificationCountResponse from(Integer count) {
        return NotificationCountResponse.builder()
                .count(count)
                .build();
    }
}
