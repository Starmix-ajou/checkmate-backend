package com.starmix.checkmate.adapter.in.sse.common;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.*;

@Component
public class SseEmitterManager {
    private final ConcurrentMap<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitterManager() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::sendKeepAlive, 15, 15, TimeUnit.SECONDS);
    }

    public SseEmitter addEmitter(SseType sseType, String userId) {
        SseEmitter emitter = new SseEmitter(0L);
        emitters.put(sseType.getPrefix() + userId, emitter);

        emitter.onCompletion(() -> emitters.remove(sseType.getPrefix() + userId));
        emitter.onTimeout(() -> emitters.remove(sseType.getPrefix() + userId));
        emitter.onError((e) -> emitters.remove(sseType.getPrefix() + userId));

        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("init")
                        .data("connected"));
            } catch (IOException e) {
                emitters.remove(sseType.getPrefix() + userId);
            }
        });

        return emitter;
    }

    public void sendEventTo(SseType sseType, String userId, String eventName, Object data) {
        SseEmitter emitter = emitters.get(sseType.getPrefix() + userId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name(eventName).data(data));
            } catch (IOException e) {
                emitters.remove(sseType.getPrefix() + userId);
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