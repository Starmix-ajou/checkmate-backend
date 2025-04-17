package com.starmix.checkmate.application.port.out.slack;

import com.starmix.checkmate.adapter.out.slack.log.SlackLabel;
import com.starmix.checkmate.adapter.out.slack.log.SlackLogLevel;

import java.time.LocalDateTime;

public interface SlackPort {
    void sendMsg(LocalDateTime timestamp, String title, String description, SlackLogLevel logLevel, SlackLabel label);
}
