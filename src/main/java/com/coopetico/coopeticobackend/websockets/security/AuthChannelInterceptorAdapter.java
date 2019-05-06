package com.coopetico.coopeticobackend.websockets.security;

import com.coopetico.coopeticobackend.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * Contiene la lógica del interceptor de mensajes de websockets para poner hacer la autenticación con los tokens
 */
@Component
public class AuthChannelInterceptorAdapter implements ChannelInterceptor {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthChannelInterceptorAdapter(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Intercepta el mensaje, toma el header con el token y realiza la autenticación.
     * @param message mensaje enviado por el usuario
     * @param channel canal por donde está pasando el mensaje
     * @return retorna el mensaje para que siga el flujo
     * @throws AuthenticationException en caso de que el usuario no haya dado un token válido
     */
    @Override
    public Message<?> preSend(final Message<?> message, final MessageChannel channel) throws AuthenticationException {
        final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        assert accessor != null;
        // Si el usuario se está conectando se verifica si pasó un token
        if (StompCommand.CONNECT == accessor.getCommand()) {
            //Extraer el token
            final String bearerToken = accessor.getFirstNativeHeader(AUTHORIZATION_HEADER);
            String token = null;
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                token =  bearerToken.substring(7);
            }

            //Validar el token
            if (token != null && jwtTokenProvider.validateToken(token)) {
                //Si el token es válido, se guarda en la sesión del websocket la información del mismo
                UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken)jwtTokenProvider.getAuthentication(token);
                accessor.setUser(auth);
            }
        }
        return message;
    }
}