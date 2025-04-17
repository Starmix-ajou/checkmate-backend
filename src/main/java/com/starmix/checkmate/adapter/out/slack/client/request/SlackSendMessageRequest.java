package com.starmix.checkmate.adapter.out.slack.client.request;

import lombok.Builder;

import java.util.List;

@Builder
public record SlackSendMessageRequest(
        String text,
        List<Object> blocks
) {}