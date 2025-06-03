package com.starmix.checkmate.adapter.in.sse.common;

import com.starmix.checkmate.infrastructure.security.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.*;

@Component
public class SseEmitterManager {
    private final ConcurrentMap<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final JwtUtil jwtUtil;

    public SseEmitterManager(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::sendKeepAlive, 15, 15, TimeUnit.SECONDS);
    }

    public SseEmitter addEmitter(String userId) {
        SseEmitter emitter = new SseEmitter(0L);
        emitters.put(userId, emitter);

        emitter.onCompletion(() -> emitters.remove(userId));
        emitter.onTimeout(() -> emitters.remove(userId));
        emitter.onError((e) -> emitters.remove(userId));

        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("init")
                        .data("connected"));
            } catch (IOException e) {
                emitters.remove(userId);
            }
        });

        return emitter;
    }

    public void sendEventTo(String userId, String eventName, Object data) {
        SseEmitter emitter = emitters.get(userId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name(eventName).data(data));
            } catch (IOException e) {
                emitters.remove(userId);
            }
        }
    }

    public void sendEvent(String eventName, Object data) {
        String userId = jwtUtil.extractUser().getUserId();
        SseEmitter emitter = emitters.get(userId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name(eventName).data(data));
            } catch (IOException e) {
                emitters.remove(userId);
            }
        }
    }

    private void sendKeepAlive() {
        for (Map.Entry<String, SseEmitter> entry : emitters.entrySet()) {
            try {
                entry.getValue().send(SseEmitter.event().comment("keep-alive"));
            } catch (IOException e) {
                emitters.remove(entry.getKey());
            }
        }
    }
}