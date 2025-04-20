package com.starmix.checkmate.adapter.out.slack.adapter;

import com.starmix.checkmate.adapter.out.slack.client.SlackFeignClient;
import com.starmix.checkmate.adapter.out.slack.client.request.SlackSendMessageFeignRequest;
import com.starmix.checkmate.adapter.out.slack.log.SlackLabel;
import com.starmix.checkmate.adapter.out.slack.log.SlackLogLevel;
import com.starmix.checkmate.application.port.out.slack.SlackPort;
import com.starmix.checkmate.global.exception.CustomException;
import com.starmix.checkmate.infrastructure.config.SpringEnv;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class SlackAdapter implements SlackPort {

    private final SpringEnv springEnv;
    private final SlackFeignClient slackFeignClient;

    @Override
    public void sendMsg(LocalDateTime timestamp, String title, String description, SlackLogLevel logLevel, SlackLabel label) {
        try {
            SlackSendMessageFeignRequest slackSendMessageRequest = createMessage(
                    timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    title, description, logLevel, label
            );
            ResponseEntity<String> response = slackFeignClient.sendMessage(slackSendMessageRequest);

            if (response.getStatusCode() != HttpStatus.OK) {
                log.error("메시지 전송 실패 - 응답 코드: {}, 응답 메시지: {}", response.getStatusCode(), response.getBody());
                throw new CustomException("메시지 전송 실패[" + response.getStatusCode() +"] 응답 메시지: " + response.getBody(), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            log.error("메시지 전송 중 에러 발생 :: ", e);
            throw new CustomException("메시지 전송 중 에러 발생 :: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private SlackSendMessageFeignRequest createMessage(String timestamp, String title, String description, SlackLogLevel logLevel, SlackLabel label) {
        String text = label.getIcon() + " " + title;

        List<Object> blocks = List.of(
                Map.of(
                        "type", "header",
                        "text", Map.of("type", "plain_text", "text", text)
                ),
                Map.of(
                        "type", "section",
                        "fields", List.of(
                                Map.of("type", "mrkdwn", "text", "*로그 레벨*\n[" + logLevel.name() + "] " + label.name()),
                                Map.of("type", "mrkdwn", "text", "*발생 시간*\n" + timestamp),
                                Map.of("type", "mrkdwn", "text", "*발생 환경*\n" + springEnv.getProfile())
                        )
                ),
                Map.of("type", "divider"),
                Map.of(
                        "type", "section",
                        "text", Map.of("type", "mrkdwn", "text", "*상세 내용*\n```" + description + "```")
                )
        );

        return SlackSendMessageFeignRequest.builder()
                .text(text)
                .blocks(blocks)
                .build();
    }
}
