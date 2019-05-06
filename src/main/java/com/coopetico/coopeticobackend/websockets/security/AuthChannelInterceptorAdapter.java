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

@Component
public class AuthChannelInterceptorAdapter implements ChannelInterceptor {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthChannelInterceptorAdapter(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Message<?> preSend(final Message<?> message, final MessageChannel channel) throws AuthenticationException {
        final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        assert accessor != null;
        if (StompCommand.CONNECT == accessor.getCommand()) {
            final String bearerToken = accessor.getFirstNativeHeader(AUTHORIZATION_HEADER);
            String token = null;
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                token =  bearerToken.substring(7);
            }

            if (token != null && jwtTokenProvider.validateToken(token)) {
                UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken)jwtTokenProvider.getAuthentication(token);
                accessor.setUser(auth);
            }
        }
        return message;
    }
}