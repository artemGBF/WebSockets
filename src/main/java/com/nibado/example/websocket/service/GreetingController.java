package com.nibado.example.websocket.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpAttributesContextHolder;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.user.SimpSession;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class GreetingController {

    @MessageMapping({"/pong"})
    public void pong(@Payload Message message) throws Exception {
        log.info("Received pong: {}", message.getContent());
    }
}
