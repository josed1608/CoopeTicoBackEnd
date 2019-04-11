package com.coopetico.coopeticobackend.controladores;
// Programador: Hannia Aguilar Salas
// Fecha: 11/04/2019
// Version: 2.0
// Controlador de usuarios, hice los métodos de actualizarContrasena: Cambiar la contraseña en la base de datos
// y mostrarInterfazCambioContrasena: Validar el token que se envío al mail del usuario para el cambio de contraseña.

import com.coopetico.coopeticobackend.entidades.TokenRecuperacionContrasenaEntidad;
import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.mail.EmailServiceImpl;
import com.coopetico.coopeticobackend.repositorios.UsuariosRepositorio;
import com.coopetico.coopeticobackend.servicios.TokensRecuperacionContrasenaServicio;
import com.coopetico.coopeticobackend.servicios.TokensRecuperacionContrasenaServicioImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Calendar;


@Controller    // This means that this class is a Controller
@RequestMapping(path="/usuarios") // This means URL's start with /demo (after Application path)
public class UsuarioControlador {

    private TokensRecuperacionContrasenaServicioImpl tokensServicio;
    private EmailServiceImpl mail ;
    private UsuariosRepositorio usuariosRepositorio;
    private PasswordEncoder encoder;
    private TokensRecuperacionContrasenaServicio tokensRecuperacionContrasenaServicio;

    @Autowired
    public UsuarioControlador(UsuariosRepositorio usuariosRepositorio, PasswordEncoder encoder, TokensRecuperacionContrasenaServicio tokensRecuperacionContrasenaServicio) {
        this.usuariosRepositorio = usuariosRepositorio;
        this.encoder = encoder;
        this.tokensRecuperacionContrasenaServicio = tokensRecuperacionContrasenaServicio;
    }

    /**
     * Método que crea un nuevo token de cambio de contraseña para el usuario, lo ingresa en la base de datos y envía el email.
     * @param correo Correo del usuario
     * @return El JWT en caso de un éxito o fallo.
     */
    @GetMapping(path="/contrasenaToken")
    public @ResponseBody ResponseEntity recuperarContrasena (@RequestParam("correo") String correo) {
        String token = tokensServicio.insertarToken(correo);
        if (token == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        mail.sendSimpleMessage(correo, "Codigo de reseteo", token);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     *  Método que recibe el id (correo) y la nueva contraseña
     * @param datosUsuario Modelo del request de autenticación. Espera los atributos username y password
     * @return El JWT en caso de un cambio exitoso o fallido.
     */
    @PutMapping(path = "/recuperarContrasena")
    public ResponseEntity actualizarContrasena(@RequestBody AuthenticationRequest datosUsuario){
        //Obtener el usuario con ese nombre o correo
        String nombreUsuario = datosUsuario.getUsername();
        UsuarioEntidad usuarioEntidad = this.usuariosRepositorio.findById(nombreUsuario).orElseThrow(null);

        if(usuarioEntidad != null){
            //Encriptar la contraseña
            usuarioEntidad.setContrasena(encoder.encode(datosUsuario.getPassword()));
            //Actualizar el usuario
            usuariosRepositorio.save(usuarioEntidad);
            // Se borra de la tabla el Token
            tokensRecuperacionContrasenaServicio.eliminarToken(nombreUsuario);
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    ///// BEGIN -- PRUEBA DE VALIDACION DE TOKEN
      @GetMapping(path="/recuperarContrasenaEnProceso")
      public @ResponseBody String linkRecuperarContrasena (@RequestParam("id") String id) {
          return "MOSTRAR INTERFAZ DE RECUPERACIÓN DE CONTRASEÑA";
      }
      @GetMapping(path="/recuperarContrasenaFallido")
      public @ResponseBody String linkRecuperarContrasena2 () {
          return "NO MOSTRAR INTERFAZ DE RECUPERACIÓN DE CONTRASEÑA";
      }
     ///// END -- PRUEBA DE VALIDACION DE TOKEN

    /**
     * Método que revisa el token de cambio de contraseña con el que ingresa el usuario (link enviado al correo)
     * Valida el token y lo redirecciona a realizar el cambio o a un error en el link.
     * @param id Correo del usuario
     * @param token Token del link enviado al correo, generado para el cambio de contraseña
     * @return Retorna la dirección a la que se dirije al usuario.
     */
    @RequestMapping(value = "/recuperarContrasena", method = RequestMethod.GET)
    public String mostrarInterfazCambioContrasena( @RequestParam("id") String id, @RequestParam("token") String token) {
        System.out.println("Intenta entrar a recuperar contrasena");
        boolean resultadoValidacion = validarTokenRecuperarContrasena(id);
        if (!resultadoValidacion) {
            return "redirect:/usuarios/recuperarContrasenaFallido";
        }
        return "redirect:/usuarios/recuperarContrasenaEnProceso?id=" + id;
    }

    /**
     * Método para validar el token, revisa que exista un token para ese usuario (correo) y que la fecha del link no haya expirado.
     * @param id Correo del usuario
     * @return Boolean que indica si es válido o no.
     */
    private boolean validarTokenRecuperarContrasena(String id) {
        TokenRecuperacionContrasenaEntidad tokenContrasena  = tokensRecuperacionContrasenaServicio.getToken(id);
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