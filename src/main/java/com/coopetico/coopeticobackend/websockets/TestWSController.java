package com.coopetico.coopeticobackend.websockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * Clase para crear métodos de testing para destinos de aplicación de los web sockets
 */
@Controller
public class TestWSController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    /**
     * Se llama cuando un usuario se suscribe a /app/test
     * @param principal usuario que está autenticado a la hora de suscribirse
     * @return retorna el id del usuario que se suscribió
     */
    @SubscribeMapping("/test")
    public String retrieveParticipants(Principal principal) {
        return principal.getName();
    }

    /**
     * Endpoint para probar mensajes con destino de usuario
     * @param mensaje mensaje enviado por el usuario que se le reenviará
     * @param principal EL usuario autenticado que se suscribió
     * @return el mensaje enviado por el usuario
     */
    @MessageMapping("/user/queue/test")
    @SendToUser("/queue/test")
    public String cambiarMensaje2(@Payload String mensaje, Principal principal){
        System.out.println("hola: " + mensaje);
        simpMessagingTemplate.convertAndSend("/user/" + principal.getName() + "/queue/test ", mensaje);
        return mensaje;
    }

}
