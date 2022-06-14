package com.nibado.example.websocket.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class SocketSessionHandler extends StompSessionHandlerAdapter {
    private Map<String, StompSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        sessions.put(session.getSessionId(), session);
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
            StompSession stompSession = sessions.get(headers.getSession());
        }
    }
}
