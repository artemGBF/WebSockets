package com.nibado.example.websocket.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class GreetingController {

    @MessageMapping({"/pong"})
    public void pong(Message message) throws Exception {
        log.info("Received pong: {}", message.getContent());
    }
}
