package com.coopetico.coopeticobackend.security.jwt;

import com.coopetico.coopeticobackend.excepciones.InvalidJwtAuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Filtro que utiliza por debajo el JwtProvider para la lógica de autenticación de los tokens
 * @author      Jose David Vargas Artavia
 */
public class JwtTokenFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;

    JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Método de la clase padre que se sobreescribe para configurar el filtro de JWT
     *
     * @param req request del cliente
     * @param res respuesta del servidor
     * @param filterChain cadena de filtros de Spring security
     */
    @Override
    public void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        try {
            String token = jwtTokenProvider.resolveToken(req);
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            filterChain.doFilter(req, res);
        }
        catch (InvalidJwtAuthenticationException e) {
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
            res.getWriter().write(e.getMessage());
        }
    }

}