package com.coopetico.coopeticobackend.controladores;

import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.repositorios.UsuariosRepositorio;
import com.coopetico.coopeticobackend.security.CustomUserDetails;
import com.coopetico.coopeticobackend.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    UsuariosRepositorio users;

    @Autowired
    public AuthControlador(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UsuariosRepositorio users) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.users = users;
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
            UsuarioEntidad usuarioEntidad = this.users.findById(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found"));
            List<String> roles = new CustomUserDetails(usuarioEntidad)
                    .getAuthorities()
                    .stream()
                    .map(SimpleGrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            String token = jwtTokenProvider.createToken(username, roles, usuarioEntidad.getGrupoByIdGrupo().getPkId());

            return ok(token);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    /**
     * Endpoint de prueba asegurado para probar que el filtro del JWT sirva
     *
     * @return Lista con todos los usuarios del sistema
     */
    @GetMapping("/usuarios")
    public List<UsuarioEntidad> todosLosUsuarios() {
        return users.findAll();
    }
}
