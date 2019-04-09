package com.coopetico.coopeticobackend.controladores;

import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.repositorios.UsuariosRepositorio;
import com.coopetico.coopeticobackend.security.PasswordEncoderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Random;


@Controller    // This means that this class is a Controller
@RequestMapping(path="/usuarios") // This means URL's start with /demo (after Application path)
public class UsuarioControlador {

    private UsuariosRepositorio usuariosRepositorio;

    @Autowired
    private PasswordEncoderImpl encoder;

    @Autowired
    public UsuarioControlador(UsuariosRepositorio usuariosRepositorio, PasswordEncoderImpl encoder) {
        this.usuariosRepositorio = usuariosRepositorio;
        this.encoder = encoder;
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

