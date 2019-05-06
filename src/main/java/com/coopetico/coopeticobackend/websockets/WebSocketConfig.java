package com.coopetico.coopeticobackend.websockets;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * Clase de configuración de los web sockets
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    /**
     * Registra los endpoints donde se pueden concectar a los web sockets
     * @param registry clase que contiene la configuración de los websockets
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /// Se agrega el path /ws como un punto para concetarse y crear un websocket con el server
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();;
    }

    /**
     * Configura los endpoints del Broker de STOMP y para detinos de aplicación
     *
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // /topic es para broadcasting y /queue es para mensajes a usuarios específicos
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("/app");
    }
}
