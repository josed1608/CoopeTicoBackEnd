package com.coopetico.coopeticobackend.controladores;

import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.repositorios.UsuariosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;


@Controller    // This means that this class is a Controller
@RequestMapping(path="/usuario") // This means URL's start with /demo (after Application path)
public class UsuarioControlador {

    private UsuariosRepositorio usuariosRepositorio;
    private PasswordEncoder encoder;
    //private TokensRecuperacionContrasenaRepositorio tokensRecuperacionContrasenaRepositorio;


    @Autowired
    public UsuarioControlador(UsuariosRepositorio usuariosRepositorio, PasswordEncoder encoder) {
        this.usuariosRepositorio = usuariosRepositorio;
        this.encoder = encoder;
        //this.tokensRecuperacionContrasenaRepositorio = tokensRecuperacionContrasenaRepositorio;
    }

    @PutMapping(path = "/recuperarContrasena")
    public ResponseEntity actualizarContrasena(@RequestBody AuthenticationRequest datosUsuario){
        String nombreUsuario = datosUsuario.getUsername();
        //Obtener el usuario con ese nombre o correo
        UsuarioEntidad usuarioEntidad = this.usuariosRepositorio.findById(nombreUsuario).orElseThrow(null);
        if(usuarioEntidad != null){
            //Encriptar la contraseña
            usuarioEntidad.setContrasena(encoder.encode(datosUsuario.getPassword()));
            //System.out.println("CONTRASENNA" + usuarioEntidad.getContrasena());
            //Actualizar el usuario
            usuariosRepositorio.save(usuarioEntidad);

            // TODO BORRAR TOKEN EN LA TABLA

            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    ///// BEGIN -- PRUEBA DE VALIDACION DE TOKEN
    @GetMapping(path="/recuperarContrasena")
    public @ResponseBody String linkRecuperarContrasena () {
        return "MOSTRAR INTERFAZ DE RECUPERACIÓN DE CONTRASEÑA";
    }

    @GetMapping(path="/recuperarContrasenaFallido")
    public @ResponseBody String linkRecuperarContrasena2 () {
        return "NO MOSTRAR INTERFAZ DE RECUPERACIÓN DE CONTRASEÑA";
    }
    ///// END -- PRUEBA DE VALIDACION DE TOKEN

    @RequestMapping(value = "/recuperarContrasena", method = RequestMethod.GET)
    public String mostrarInterfazCambioContrasena( @RequestParam("id") String id, @RequestParam("token") String token) {
        System.out.println("Intenta entrar a recuperar contrasena");
         boolean resultadoValidacion = validarTokenRecuperarContrasena(id, token);
        if (!resultadoValidacion) {
            return "redirect:/usuario/recuperarContrasenaFallido";
        }
        // TODO Se necesita el id en el path para hacer el cambio de contraseña, verdad?
        return "redirect:/usuario/recuperarContrasena?id=" + id;
    }

    private boolean validarTokenRecuperarContrasena(String id, String token) {
        // TODO Implementar el findByToken
        // TokenRecuperacionContrasenaEntidad tokenContrasena  = tokensRecuperacionContrasenaRepositorio.findByToken(token);
        // if ((tokenContrasena == null || (tokenContrasena.getFkCorreoUsuario() != id)) {
        //        return false;
        // }

        Calendar calendario = Calendar.getInstance();
        // if ((tokenContrasena.getExpiryDate()
        //         .getTime() - cal.getTime()
        //         .getTime()) <= 0) {
        //     return false;
        // }

        return true;
    }
}

