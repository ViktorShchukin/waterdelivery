package com.example.waterdelivery.service;

import com.example.waterdelivery.model.OrderStatusMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class OrderSseService {
    private final Map<UUID, SseEmitter> emitters =
            new ConcurrentHashMap<>();

    public SseEmitter subscribe(UUID userId) {

        SseEmitter emitter = new SseEmitter(0L);
        emitters.put(userId, emitter);

        emitter.onCompletion(() -> emitters.remove(userId));
        emitter.onTimeout(() -> emitters.remove(userId));
        emitter.onError(e -> emitters.remove(userId));

        return emitter;
    }

    public void send(UUID userId, OrderStatusMessage message) {
        SseEmitter emitter = emitters.get(userId);
        if (emitter == null) {
            return;
        }

        try {
            emitter.send(
                    SseEmitter.event()
                            .name("change-order-status")
                            .data(message)
            );
        } catch (IOException e) {
            emitters.remove(userId);
            log.error("Can't send order status update to userId {}, {}", userId, message);
        }
    }
}
