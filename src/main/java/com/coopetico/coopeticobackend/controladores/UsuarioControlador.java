package com.coopetico.coopeticobackend.controladores;

import com.coopetico.coopeticobackend.entidades.TokenRecuperacionContrasenaEntidad;
import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.repositorios.TokensRecuperacionContrasenaRepositorio;
import com.coopetico.coopeticobackend.repositorios.UsuariosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.coopetico.coopeticobackend.mail.EmailServiceImpl;
import com.coopetico.coopeticobackend.servicios.TokensRecuperacionContrasenaServicioImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller    // This means that this class is a Controller
@RequestMapping(path="/usuario") // This means URL's start with /demo (after Application path)
public class UsuarioControlador {

    private TokensRecuperacionContrasenaServicioImpl tokensServicio;

    private EmailServiceImpl mail ;

    private UsuariosRepositorio usuariosRepositorio;
    private PasswordEncoder encoder;
    private TokensRecuperacionContrasenaRepositorio tokensRecuperacionContrasenaRepositorio;


    @Autowired
    public UsuarioControlador(UsuariosRepositorio usuariosRepositorio, PasswordEncoder encoder, TokensRecuperacionContrasenaRepositorio tokensRecuperacionContrasenaRepositorio) {
        this.usuariosRepositorio = usuariosRepositorio;
        this.encoder = encoder;
        this.tokensRecuperacionContrasenaRepositorio = tokensRecuperacionContrasenaRepositorio;
    }

    @GetMapping(path="/contrasenaToken")
    public @ResponseBody ResponseEntity recuperarContrasena (@RequestParam("correo") String correo) {
        String token = tokensServicio.insertarToken(correo);
        if (token == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        mail.sendSimpleMessage(correo, "Codigo de reseteo", token);
        return new ResponseEntity(HttpStatus.OK);
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
            tokensRecuperacionContrasenaRepositorio.deleteById(nombreUsuario);

            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    ///// BEGIN -- PRUEBA DE VALIDACION DE TOKEN
      @GetMapping(path="/recuperarContrasenaEnProceso")
      public @ResponseBody String linkRecuperarContrasena (@RequestParam("id") String id) {
          return "MOSTRAR INTERFAZ DE RECUPERACIÓN DE CONTRASEÑA";
      }      @GetMapping(path="/recuperarContrasenaFallido")
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
        return "redirect:/usuario/recuperarContrasenaEnProceso?id=" + id;
    }

    private boolean validarTokenRecuperarContrasena(String id, String token) {
        // TODO Implementar el findByToken
        List<TokenRecuperacionContrasenaEntidad> tokenContrasenaLista  = tokensRecuperacionContrasenaRepositorio.findByToken(token);

        if(!tokenContrasenaLista.isEmpty()) {
            TokenRecuperacionContrasenaEntidad tokenContrasena = tokenContrasenaLista.get(0);

           //if (tokenContrasena == null || !tokenContrasena.getFkCorreoUsuario().equals(id)) {
           //    return false;
           //}

           //Calendar calendario = Calendar.getInstance();
           //if ((tokenContrasena.getFechaExpiracion()
           //        .getTime() - calendario.getTime()
           //        .getTime()) <= 0) {
           //    return false;
           //}

            return true;
        }

        return false;
    }
}