package com.coopetico.coopeticobackend.websockets.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

/**
 * Contiene la configuración de alto nivel de seguridad de los websockets
 */
@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    /**
     * Configura los permisos y reglas para acceder a los recursos de websockets
     * @param messages contiene la configuración de seguridad
     */
    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
                .simpSubscribeDestMatchers("/topic/test").authenticated()
                .anyMessage().authenticated();
    }

    /**
     * Desactiva o activa CORS
     * @return true para desactivar CORS o false para activarlo
     */
    @Override
    protected boolean sameOriginDisabled() {
        //disable CSRF for websockets for now...
        return true;
    }
}