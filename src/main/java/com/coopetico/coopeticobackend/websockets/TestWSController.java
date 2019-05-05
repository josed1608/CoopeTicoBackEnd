package com.coopetico.coopeticobackend.websockets;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class TestWSController {
    @SubscribeMapping("/test")
    public String retrieveParticipants() {
        return "Thanks for subscribing";
    }

}
