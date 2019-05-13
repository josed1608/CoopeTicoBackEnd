package com.coopetico.coopeticobackend.controladores;

import com.coopetico.coopeticobackend.entidades.bd.UsuarioEntidad;
import com.coopetico.coopeticobackend.excepciones.InvalidJwtAuthenticationException;
import com.coopetico.coopeticobackend.excepciones.MalasCredencialesExcepcion;
import com.coopetico.coopeticobackend.excepciones.UsuarioNoEncontradoExcepcion;
import com.coopetico.coopeticobackend.security.jwt.JwtTokenProvider;
import com.coopetico.coopeticobackend.servicios.UsuarioServicio;
import com.coopetico.coopeticobackend.entidades.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Controlador para los request relacionados con autenticación
 * @author      Jose David Vargas Artavia
 */
@RestController
@RequestMapping("/auth")
public class AuthControlador {

    private final
    AuthenticationManager authenticationManager;

    private final
    JwtTokenProvider jwtTokenProvider;

    private final
    UsuarioServicio usuarioServicio;

    @Autowired
    public AuthControlador(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UsuarioServicio users) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioServicio = users;
    }

    /**
     * Endpoint para hacer sign in
     *
     * @param data Modelo del request de autenticación. Espera los atributos username y password
     * @return el JWT en caso de un sign in exitoso
     */
    @CrossOrigin
    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody AuthenticationRequest data) {
        try {
            String username = data.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            UsuarioEntidad usuarioEntidad = this.usuarioServicio.usuarioPorCorreo(username).orElseThrow(() -> new UsuarioNoEncontradoExcepcion("Usuario " + username + " no encontrado", HttpStatus.NOT_FOUND, System.currentTimeMillis()));
            List<String> roles = usuarioServicio.obtenerPermisos(usuarioEntidad);
            String token = jwtTokenProvider.createToken(usuarioEntidad, roles);

            return ok(token);
        } catch (AuthenticationException e) {
            throw new MalasCredencialesExcepcion("Correo o contraseña inválido", HttpStatus.UNAUTHORIZED, System.currentTimeMillis());
        }
    }

    /**
     * Endpoint para validar un token que se le pase en el body del request
     *
     * @param token string del token que se pasa en el body
     * @return devuelve true si el token es válido o una excepción si el token es inválido
     */
    @CrossOrigin
    @GetMapping("/validar-token")
    public boolean validarToken(@RequestBody String token) throws InvalidJwtAuthenticationException {
        return jwtTokenProvider.validateToken(token);
    }
}
