package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.GrupoEntidad;
import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.excepciones.CorreoTomadoExcepcion;
import com.coopetico.coopeticobackend.excepciones.GrupoNoExisteExcepcion;
import com.coopetico.coopeticobackend.repositorios.GruposRepositorio;
import com.coopetico.coopeticobackend.repositorios.UsuariosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServicioImpl implements UsuarioServicio{
    private final UsuariosRepositorio usuariosRepositorio;
    private final GruposRepositorio gruposRepositorio;
    private final PasswordEncoder encoder;

    @Autowired
    public UsuarioServicioImpl(UsuariosRepositorio usuariosRepositorio, GruposRepositorio gruposRepositorio, PasswordEncoder encoder) {
        this.usuariosRepositorio = usuariosRepositorio;
        this.gruposRepositorio = gruposRepositorio;
        this.encoder = encoder;
    }

    @Override
    public void agregarUsuario(UsuarioEntidad usuarioSinGrupo, String grupoId) throws GrupoNoExisteExcepcion, CorreoTomadoExcepcion {
        GrupoEntidad grupoUsuario = gruposRepositorio.findById(grupoId).orElseThrow(() -> new GrupoNoExisteExcepcion("Grupo de permisos no existe", HttpStatus.NOT_FOUND, System.currentTimeMillis()));

        if (usuariosRepositorio.findById(usuarioSinGrupo.getPkCorreo()).isPresent()) {
            throw new CorreoTomadoExcepcion("Correo tomado", HttpStatus.BAD_REQUEST, System.currentTimeMillis());
        }

        usuarioSinGrupo.setGrupoByIdGrupo(grupoUsuario);
        usuarioSinGrupo.setContrasena(encoder.encode(usuarioSinGrupo.getContrasena()));
        usuariosRepositorio.save(usuarioSinGrupo);
    }

    @Override
    public Optional<UsuarioEntidad> usuarioPorCorreo(String correo) {
        return usuariosRepositorio.findById(correo);
    }
}
