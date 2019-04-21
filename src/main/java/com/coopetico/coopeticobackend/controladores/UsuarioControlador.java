package com.coopetico.coopeticobackend.controladores;

/**
 Controlador de usuarios, hice los métodos de actualizarContrasena: Cambiar la contraseña en la base de datos
 y mostrarInterfazCambioContrasena: Validar el token que se envío al mail del usuario para el cambio de contraseña.
 @author      Hannia Aguilar Salas
 @since       19-04-2019
 @version:   3.0
 */


import com.coopetico.coopeticobackend.entidades.GrupoEntidad;
import com.coopetico.coopeticobackend.entidades.TokenRecuperacionContrasenaEntidad;
import com.coopetico.coopeticobackend.mail.EmailServiceImpl;
import com.coopetico.coopeticobackend.repositorios.UsuariosRepositorio;
import com.coopetico.coopeticobackend.servicios.TokensRecuperacionContrasenaServicio;
import com.coopetico.coopeticobackend.servicios.TokensRecuperacionContrasenaServicioImpl;
import com.coopetico.coopeticobackend.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.repositorios.UsuariosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.*;
import java.util.Calendar;
import java.util.List;
import javax.validation.constraints.Email;

@RestController
@RequestMapping(path="/usuarios")
@Validated
public class UsuarioControlador {

    @Autowired
    private TokensRecuperacionContrasenaServicioImpl tokensServicio;
    @Autowired
    private EmailServiceImpl mail;
    private UsuariosRepositorio usuariosRepositorio;
    private PasswordEncoder encoder;
    private UsuarioServicio usuarioServicio;

    @Autowired
    public UsuarioControlador(UsuariosRepositorio usuariosRepositorio, PasswordEncoder encoder, TokensRecuperacionContrasenaServicio tokensRecuperacionContrasenaServicio, UsuarioServicio servicio, EmailServiceImpl mail) {
        this.usuarioServicio = servicio;
        this.usuariosRepositorio = usuariosRepositorio;
        this.encoder = encoder;
        //this.tokensRecuperacionContrasenaServicio = tokensRecuperacionContrasenaServicio;
        //this.mail = mail;
    }

    /**
     * Método que crea un nuevo token de cambio de contraseña para el usuario, lo ingresa en la base de datos y envía el email.
     * @param correo Correo del usuario
     * @return El JWT en caso de un éxito o fallo.
     */
    @GetMapping(path="/recuperarContrasena/{correo}")
    public boolean recuperarContrasena (@PathVariable String correo) {
        String token = tokensServicio.insertarToken(correo);
        if (token == null) {
            return false;
        }
        mail.enviarCorreoRecuperarContrasena(correo, token);
        return true;
    }

    /**
     *  Método que recibe el id (correo) y la nueva contraseña
     * @param datosUsuario Modelo del request de autenticación. Espera los atributos username y password
     * @return El JWT en caso de un cambio exitoso o fallido.
     */
    @PutMapping(path = "/cambiarContrasena")
    @ResponseStatus(HttpStatus.OK)
    public boolean cambiarContrasena(@RequestBody AuthenticationRequest datosUsuario){
        //Obtener el usuario con ese nombre o correo
        if(datosUsuario.getUsername() != null & datosUsuario.getPassword() != null) {

            if (this.usuarioServicio.usuarioPorCorreo(datosUsuario.getUsername()).isPresent()) {
                UsuarioEntidad usuarioEntidad = this.usuarioServicio.usuarioPorCorreo(datosUsuario.getUsername()).get();
                //Encriptar la contraseña
                usuarioEntidad.setContrasena(encoder.encode(datosUsuario.getPassword()));
                //Actualizar el usuario
                usuariosRepositorio.save(usuarioEntidad);
                // Se borra de la tabla el Token
                tokensServicio.eliminarToken(datosUsuario.getUsername());
                return true;
            }

            return false;
        }

        return false;
    }

    /**
     * Método para validar el token, revisa que exista un token para ese usuario (correo) y que la fecha del link no haya expirado.
     * @param id Correo del usuario
     * @param token Token del link de recuperación.
     * @return Boolean que indica si es válido o no.
     */
    @GetMapping("/cambiarContrasena/{id}/{token}")
    @ResponseStatus(HttpStatus.OK)
    public boolean validarTokenRecuperarContrasena(@PathVariable String id, @PathVariable String token) {
        TokenRecuperacionContrasenaEntidad tokenContrasena  = tokensServicio.getToken(id);
        //Validación con el usuario
        if (tokenContrasena == null || !tokenContrasena.getFkCorreoUsuario().equals(id) || !tokenContrasena.getToken().equals(token)) {
            return false;
        }

        //Revisa la fecha del link
        Calendar calendario = Calendar.getInstance();
        if ((tokenContrasena.getFechaExpiracion()
                .getTime() - calendario.getTime()
                .getTime()) <= 0) {
            return false;
        }

        return true;
    }

    //TODO ver lo de acoplamiento y cohesion
    @PostMapping("/usuarios")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioEntidad crearUsuario(@RequestBody UsuarioEntidad usuario){
        String grupo = usuario.getGrupoByIdGrupo().getPkId();
        return usuarioServicio.agregarUsuario(usuario, grupo);
    }

    @GetMapping("/usuarios/{id}")
    public UsuarioEntidad obtenerUsuarioPorId(@PathVariable String id){
        return usuarioServicio.usuarioPorCorreo(id).get();
    }

    /***
     * Borra un usuario del sistema.
     *
     * @param id Correo del usuario a borrar
     * @return Si el correo pertenece a un usuario valido, retorna OK, si no, NOT_FOUND
     */
    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity eliminarUsuarioPorId(@PathVariable String id){
        try {
            usuarioServicio.eliminar(id);
        }catch (UsernameNotFoundException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/usuarios")
    public List<UsuarioEntidad> obtenerUsuarios(){
        return usuarioServicio.obtenerUsuarios();
    }

    @GetMapping("/grupo")
    public List<UsuarioEntidad> obtenerUsuariosPorGrupo(@RequestBody GrupoEntidad grupoEntidad){
        return usuarioServicio.obtenerUsuariosPorGrupo(grupoEntidad);
    }

    @PutMapping("/usuarios/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioEntidad actualizar(@RequestBody UsuarioEntidad usuario, @PathVariable String id){
        UsuarioEntidad usuarioTemporal = this.usuarioServicio.usuarioPorCorreo(id).get();
        usuarioTemporal.setApellidos(usuario.getApellidos());
        usuarioTemporal.setNombre(usuario.getNombre());
        usuarioTemporal.setTelefono(usuario.getTelefono());

        return usuarioServicio.agregarUsuario(usuarioTemporal, usuarioTemporal.getPkCorreo());
    }

    /**
     * Método para validar el token, revisa que exista un token para ese usuario (correo) y que la fecha del link no haya expirado.
     * @param id Correo del usuario
     * @return Boolean que indica si es válido o no.
     */
    public boolean validarTokenRecuperarContrasena(String id) {
        TokenRecuperacionContrasenaEntidad tokenContrasena  = tokensServicio.getToken(id);
        //Validación con el usuario
        if (tokenContrasena == null || !tokenContrasena.getFkCorreoUsuario().equals(id)) {
            return false;
        }

        //Revisa la fecha del link
        Calendar calendario = Calendar.getInstance();
        if ((tokenContrasena.getFechaExpiracion()
                .getTime() - calendario.getTime()
                .getTime()) <= 0) {
            return false;
        }

        return true;
    }
}