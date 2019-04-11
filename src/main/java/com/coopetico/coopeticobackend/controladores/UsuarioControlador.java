package com.coopetico.coopeticobackend.controladores;

import com.coopetico.coopeticobackend.mail.EmailServiceImpl;
import com.coopetico.coopeticobackend.servicios.TokensRecuperacionContrasenaServicioImpl;
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

import javax.validation.constraints.Email;


@Controller    // This means that this class is a Controller
@RequestMapping(path="/usuarios") // This means URL's start with /demo (after Application path)
@Validated
public class UsuarioControlador {
    @Autowired
    TokensRecuperacionContrasenaServicioImpl tokensServicio;
    @Autowired
    EmailServiceImpl mail ;

    private UsuariosRepositorio usuariosRepositorio;

    private PasswordEncoder encoder;

    @Autowired
    public UsuarioControlador(UsuariosRepositorio usuariosRepositorio, PasswordEncoder encoder) {
        this.usuariosRepositorio = usuariosRepositorio;
        this.encoder = encoder;
    }
    
    @GetMapping(path="/contrasenaToken")
    public @ResponseBody ResponseEntity recuperarContrasena (@Email @RequestParam("correo") String correo) {
        String token = tokensServicio.insertarToken(correo);
        if (token == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND );
        }
        mail.enviarCorreoRecuperarContrasena(correo, token);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(path = "/recuperarContrasena")
    public ResponseEntity actualizarContrasena(@RequestBody AuthenticationRequest datosUsuario){
        String nombreUsuario = datosUsuario.getUsername();
        //Obtener el usuario con ese nombre o correo
        UsuarioEntidad usuarioEntidad = this.usuariosRepositorio.findById(nombreUsuario).orElseThrow(() -> new UsernameNotFoundException("Nombre de usuario " + nombreUsuario + "no encontrado"));
        //Encriptar la contraseña
        usuarioEntidad.setContrasena(encoder.encode(datosUsuario.getPassword()));
        System.out.println("CONTRASENNA" + usuarioEntidad.getContrasena());
        //Actualizar el usuario
        usuariosRepositorio.save(usuarioEntidad);
        return new ResponseEntity(HttpStatus.OK);
    }

}

