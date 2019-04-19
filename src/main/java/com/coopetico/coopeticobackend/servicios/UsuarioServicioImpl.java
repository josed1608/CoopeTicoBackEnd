package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.GrupoEntidad;
import com.coopetico.coopeticobackend.entidades.PermisosGrupoEntidad;
import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.excepciones.CorreoTomadoExcepcion;
import com.coopetico.coopeticobackend.excepciones.GrupoNoExisteExcepcion;
import com.coopetico.coopeticobackend.repositorios.GruposRepositorio;
import com.coopetico.coopeticobackend.repositorios.UsuariosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServicioImpl implements UsuarioServicio{
    private final UsuariosRepositorio usuariosRepositorio;
    private final GruposRepositorio gruposRepositorio;
    private final PasswordEncoder encoder;

    //En caso de que no se haya asignado contrasena
    private final String CONTRASENA_DEFAULT = "aguatico123";
    @Autowired
    public UsuarioServicioImpl(UsuariosRepositorio usuariosRepositorio, GruposRepositorio gruposRepositorio, PasswordEncoder encoder) {
        this.usuariosRepositorio = usuariosRepositorio;
        this.gruposRepositorio = gruposRepositorio;
        this.encoder = encoder;
    }


    @Override
    public UsuarioEntidad agregarUsuario(UsuarioEntidad usuarioSinGrupo, String grupoId) throws GrupoNoExisteExcepcion, CorreoTomadoExcepcion {
        GrupoEntidad grupoUsuario = gruposRepositorio.findById(grupoId).orElseThrow(() -> new GrupoNoExisteExcepcion("Grupo de permisos no existe", HttpStatus.NOT_FOUND, System.currentTimeMillis()));

        /**
         * Comentado porque para actualizar se necesita tener el mismo correo
        if (usuariosRepositorio.findById(usuarioSinGrupo.getPkCorreo()).isPresent()) {
            throw new CorreoTomadoExcepcion("Correo tomado", HttpStatus.BAD_REQUEST, System.currentTimeMillis());
        }**/

        usuarioSinGrupo.setGrupoByIdGrupo(grupoUsuario);
        if (usuarioSinGrupo.getContrasena() == null)
            usuarioSinGrupo.setContrasena(CONTRASENA_DEFAULT);
        usuarioSinGrupo.setContrasena(encoder.encode(usuarioSinGrupo.getContrasena()));
        return usuariosRepositorio.save(usuarioSinGrupo);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioEntidad> usuarioPorCorreo(String correo) {
        return usuariosRepositorio.findById(correo);
    }

    @Override
    public List<String> obtenerPermisos(UsuarioEntidad usuario) {
        return usuario
                .getGrupoByIdGrupo()
                .getPermisosGruposByPkId()
                .stream()
                .map(PermisosGrupoEntidad::getPermisoByPkIdPermisos)
                .map(permiso -> Integer.toString(permiso.getPkId()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioEntidad> obtenerUsuarios() {
        return usuariosRepositorio.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioEntidad> obtenerUsuariosPorGrupo(GrupoEntidad grupo) {
        return usuariosRepositorio.findByGrupoByIdGrupo(grupo);
    }

    @Override
    @Transactional
    public void eliminar(String correo) {
        usuariosRepositorio.deleteById(correo);
    }
}
