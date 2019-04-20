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
import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
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

@CrossOrigin( origins = {"http://localhost:4200"})
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
    private TokensRecuperacionContrasenaServicio tokensRecuperacionContrasenaServicio;
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
        String nombreUsuario = datosUsuario.getUsername();
        if(nombreUsuario != null) {

            if (this.usuarioServicio.usuarioPorCorreo(nombreUsuario).isPresent()) {
                UsuarioEntidad usuarioEntidad = this.usuarioServicio.usuarioPorCorreo(nombreUsuario).get();
                //Encriptar la contraseña
                usuarioEntidad.setContrasena(encoder.encode(datosUsuario.getPassword()));
                //Actualizar el usuario
                usuariosRepositorio.save(usuarioEntidad);
                // Se borra de la tabla el Token
                tokensServicio.eliminarToken(nombreUsuario);
                return true;
            }

            return false;
        }

        return false;
    }

    /**
     * Método para validar el token, revisa que exista un token para ese usuario (correo) y que la fecha del link no haya expirado.
     * @param id Correo del usuario
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

   //  ///// BEGIN -- PRUEBA DE VALIDACION DE TOKEN
   //    @GetMapping(path="/recuperarContrasenaEnProceso")
   //    public @ResponseBody String linkRecuperarContrasena (@RequestParam("id") String id) {
   //        return "MOSTRAR INTERFAZ DE RECUPERACIÓN DE CONTRASEÑA";
   //    }
   //    @GetMapping(path="/recuperarContrasenaFallido")
   //    public @ResponseBody String linkRecuperarContrasena2 () {
   //        return "NO MOSTRAR INTERFAZ DE RECUPERACIÓN DE CONTRASEÑA";
   //    }
   //   ///// END -- PRUEBA DE VALIDACION DE TOKEN
//
   //  /**
   //   * Método que revisa el token de cambio de contraseña con el que ingresa el usuario (link enviado al correo)
   //   * Valida el token y lo redirecciona a realizar el cambio o a un error en el link.
   //   * @param id Correo del usuario
   //   * @param token Token del link enviado al correo, generado para el cambio de contraseña
   //   * @return Retorna la dirección a la que se dirije al usuario.
   //   */
   //  @RequestMapping(value = "/recuperarContrasena", method = RequestMethod.GET)
   //  public String mostrarInterfazCambioContrasena( @RequestParam("usuario") String id, @RequestParam("token") String token) {
   //      System.out.println("Intenta entrar a recuperar contrasena");
   //      boolean resultadoValidacion = validarTokenRecuperarContrasena(id);
   //      if (!resultadoValidacion) {
   //          return "redirect:/usuarios/recuperarContrasenaFallido";
   //      }
   //      return "redirect:/usuarios/recuperarContrasenaEnProceso?id=" + id;
   //  }


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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarUsuarioPorId(@PathVariable String id){
        usuarioServicio.eliminar(id);
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

}