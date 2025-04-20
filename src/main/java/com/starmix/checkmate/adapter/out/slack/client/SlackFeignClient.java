package com.starmix.checkmate.adapter.out.slack.client;

import com.starmix.checkmate.adapter.out.slack.client.request.SlackSendMessageFeignRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "slackFeignClient", url = "${slack.webhook.alert.url}")
public interface SlackFeignClient {
    @PostMapping
    ResponseEntity<String> sendMessage(@RequestBody SlackSendMessageFeignRequest slackSendMessageRequest);
}