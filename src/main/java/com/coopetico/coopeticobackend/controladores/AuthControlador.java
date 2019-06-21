package com.coopetico.coopeticobackend.controladores;

import com.coopetico.coopeticobackend.entidades.bd.UsuarioEntidad;
import com.coopetico.coopeticobackend.excepciones.InvalidJwtAuthenticationException;
import com.coopetico.coopeticobackend.excepciones.MalasCredencialesExcepcion;
import com.coopetico.coopeticobackend.excepciones.UsuarioNoEncontradoExcepcion;
import com.coopetico.coopeticobackend.security.jwt.JwtTokenProvider;
import com.coopetico.coopeticobackend.servicios.TaxistasServicio;
import com.coopetico.coopeticobackend.servicios.UsuarioServicio;
import com.coopetico.coopeticobackend.entidades.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Controlador para los request relacionados con autenticación
 * @author      Jose David Vargas Artavia
 */
@RestController
@RequestMapping("/auth")
public class AuthControlador {

    private Environment environment;

    private final
    AuthenticationManager authenticationManager;

    private final
    JwtTokenProvider jwtTokenProvider;

    private final
    UsuarioServicio usuarioServicio;

    private final
    TaxistasServicio taxistasServicio;

    @Autowired
    public AuthControlador(Environment environment, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UsuarioServicio users, TaxistasServicio taxistasServicio) {
        this.environment = environment;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioServicio = users;
        this.taxistasServicio = taxistasServicio;
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

            String token = "";
            boolean esTaxista = usuarioServicio.obtenerTipo(usuarioEntidad).equals("taxista");
            if(esTaxista){
                Map estadoTaxista = taxistasServicio.obtenerEstado(usuarioEntidad.getPkCorreo());
                token = jwtTokenProvider.createToken(usuarioEntidad, roles, esTaxista, (boolean)estadoTaxista.get("estado"), (String)estadoTaxista.get("justificacion"));
            }
            else
                token = jwtTokenProvider.createToken(usuarioEntidad, roles, false, false, null);

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
    @PostMapping("/validar-token")
    public boolean validarToken(@RequestBody String token) {
        try {
           boolean validToken = jwtTokenProvider.validateToken(token);
           if (validToken) {
               return true;
           } else {
               return false;
           }
        } catch ( InvalidJwtAuthenticationException e){
            return false;
        }
    }

    /**
     * Endpoint para ver el perfl actual con el que corre la aplicacion
     * @return retorna el perfil (dev, test, prod o ci)
     */
    @GetMapping("/perfil")
    @PreAuthorize("hasAuthority('400')")
    public ResponseEntity perfilActual(){
        return ok(this.environment.getActiveProfiles());
    }
}
