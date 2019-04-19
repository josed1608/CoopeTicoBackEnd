package com.coopetico.coopeticobackend.controladores;

import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.excepciones.MalasCredencialesExcepcion;
import com.coopetico.coopeticobackend.excepciones.UsuarioNoEncontradoExcepcion;
import com.coopetico.coopeticobackend.security.jwt.JwtTokenProvider;
import com.coopetico.coopeticobackend.servicios.UsuarioServicio;
import com.coopetico.coopeticobackend.controladores.AuthenticationRequest;
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
}
