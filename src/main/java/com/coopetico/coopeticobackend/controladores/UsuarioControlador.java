package com.coopetico.coopeticobackend.controladores;

import com.coopetico.coopeticobackend.entidades.GrupoEntidad;
import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.repositorios.UsuariosRepositorio;
import com.coopetico.coopeticobackend.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path="/usuarios")
public class UsuarioControlador {

    private UsuariosRepositorio usuariosRepositorio;
    private PasswordEncoder encoder;
    private UsuarioServicio usuarioServicio;

    @Autowired
    public UsuarioControlador(UsuariosRepositorio usuariosRepositorio, PasswordEncoder encoder, UsuarioServicio servicio) {
        this.usuarioServicio = servicio;
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

    @DeleteMapping("/usuarios/{id}")
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

