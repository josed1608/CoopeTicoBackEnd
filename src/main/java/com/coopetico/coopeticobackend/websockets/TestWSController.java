package com.coopetico.coopeticobackend.websockets;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class TestWSController {
    @SubscribeMapping("/test")
    public String retrieveParticipants(Principal principal) {
        return principal.getName();
    }

}
