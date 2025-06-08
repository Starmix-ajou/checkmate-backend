package com.starmix.checkmate.adapter.in.sse.common;

import com.starmix.checkmate.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sse")
public class SseController {

    private final SseEmitterManager emitterManager;
    private final JwtUtil jwtUtil;

    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        String userId = jwtUtil.extractUser().getUserId();
        return emitterManager.addEmitter(SseType.PROJECT_SPRINT, userId);
    }

    @GetMapping(value = "/notification", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter notificationSubscribe() {
        String userId = jwtUtil.extractUser().getUserId();
        return emitterManager.addEmitter(SseType.NOTIFICATION, userId);
    }
}