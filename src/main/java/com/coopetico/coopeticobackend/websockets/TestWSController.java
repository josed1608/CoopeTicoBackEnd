package com.coopetico.coopeticobackend.websockets;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * Clase para crear métodos de testing para destinos de aplicación de los web sockets
 */
@Controller
public class TestWSController {

    /**
     * Se llama cuando un usuario se suscribe a /app/test
     * @param principal usuario que está autenticado a la hora de suscribirse
     * @return retorna el id del usuario que se suscribió
     */
    @SubscribeMapping("/test")
    public String retrieveParticipants(Principal principal) {
        return principal.getName();
    }

}
