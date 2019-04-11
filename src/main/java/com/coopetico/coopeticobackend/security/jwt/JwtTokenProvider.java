package com.coopetico.coopeticobackend.security.jwt;

import com.coopetico.coopeticobackend.excepciones.InvalidJwtAuthenticationException;
import com.coopetico.coopeticobackend.security.SecretServicio;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Clase con lógica para crear, validar y procesar JWTs
 */
@Component
public class JwtTokenProvider {

    /**
     * Tiempo de validez del token
     */
    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds = 3600000; // 1h

    private final UserDetailsService userDetailsService;

    private final SecretServicio secretkey;

    @Autowired
    public JwtTokenProvider(@Qualifier("customUserDetailsService") UserDetailsService userDetailsService, SecretServicio secretkey) {
        this.userDetailsService = userDetailsService;
        this.secretkey = secretkey;
    }

    /**
     * Crea el token de JWT con los claims deseados de usuario, rol, permisos, tiempo de creación y tiempo de expiración
     * @param username correo del usuario
     * @param permisos lista de permisos del usuario
     * @param rol rol del usuario
     * @return retorna String que representa el JWT
     */
    public String createToken(String username, List<String> permisos, String rol) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("permisos", permisos);
        claims.put("rol", rol);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, secretkey.getHS256SecretBytes())//
                .compact();
    }

    /**
     * Crea una instancia de Authentication a partir de la información del usuario que viene el token
     *
     * @param token JWT que envió el cliente
     * @return instancia de Authentication para hacer lógica de autenticación
     */
    Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * Obtiene el correo del usuario por medio de decodificar el token
     *
     * @param token token que envió el cliente
     * @return retorna el correo que venía en el token
     */
    private String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretkey.getHS256SecretBytes()).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Extrae el token del header del request del cliente
     *
     * @param req request del cliente
     * @return String que representa el token JWT
     */
    String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * Valida el token al decodificarlo con el secret key y verificar el tiempo de expiración
     *
     * @param token token JWT
     * @return retorna true si es un token válido o false si no
     */
    boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretkey.getHS256SecretBytes()).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        }
        catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token", HttpStatus.UNAUTHORIZED, System.currentTimeMillis());
        }
    }

}
