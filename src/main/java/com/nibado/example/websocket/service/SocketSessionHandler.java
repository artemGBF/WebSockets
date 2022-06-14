package com.nibado.example.websocket.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * НЕ работает!
 */
@Slf4j
@Component
public class SocketSessionHandler extends StompSessionHandlerAdapter {
    private Map<String, StompSession> sessions = new ConcurrentHashMap<>();
    private Map<String, LocalDateTime> timeouts = new ConcurrentHashMap<>();

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        String sessionId = session.getSessionId();
        sessions.put(sessionId, session);
        timeouts.put(sessionId, LocalDateTime.now());
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        exception.printStackTrace();
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Message.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        log.info("Received in frame: {}", ((Message) payload).getContent());
        if (((Message) payload).getContent().equals("PONG")) {
            String sessionId = headers.getSession();
            LocalDateTime localDateTime = timeouts.get(sessionId);
            LocalDateTime now = LocalDateTime.now();
            if (localDateTime.plusSeconds(5).isBefore(now)) {
                StompSession stompSession = sessions.get(sessionId);
                stompSession.disconnect();
            } else {
                timeouts.put(sessionId, now);
            }
        }
    }
}
