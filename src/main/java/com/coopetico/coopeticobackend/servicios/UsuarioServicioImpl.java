/***
 * Implementacion del servicio de Usuarios.
 */

package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.GrupoEntidad;
import com.coopetico.coopeticobackend.entidades.PermisosGrupoEntidad;
import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.excepciones.CorreoTomadoExcepcion;
import com.coopetico.coopeticobackend.excepciones.GrupoNoExisteExcepcion;
import com.coopetico.coopeticobackend.excepciones.UsuarioNoEncontradoExcepcion;
import com.coopetico.coopeticobackend.repositorios.GruposRepositorio;
import com.coopetico.coopeticobackend.repositorios.UsuariosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        if (usuariosRepositorio.findById(usuarioSinGrupo.getPkCorreo()).isPresent()) {
            throw new CorreoTomadoExcepcion("Correo tomado", HttpStatus.BAD_REQUEST, System.currentTimeMillis());
        }

        usuarioSinGrupo.setGrupoByIdGrupo(grupoUsuario);
        if (usuarioSinGrupo.getContrasena() == null)
            usuarioSinGrupo.setContrasena(CONTRASENA_DEFAULT);
        usuarioSinGrupo.setContrasena(encoder.encode(usuarioSinGrupo.getContrasena()));
        return usuariosRepositorio.save(usuarioSinGrupo);
    }

    @Override
    public UsuarioEntidad crearUsuario(UsuarioEntidad usuarioEntidad){
        if (usuarioEntidad.getContrasena() == null)
            usuarioEntidad.setContrasena(CONTRASENA_DEFAULT);
        usuarioEntidad.setContrasena(encoder.encode(usuarioEntidad.getContrasena()));
        return usuariosRepositorio.save(usuarioEntidad);
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
    @Transactional(readOnly = true)
    public Page<UsuarioEntidad> obtenerUsuarios(Pageable pageable) {
        return usuariosRepositorio.findAll(pageable);
    }

    /***
     * Elimina un usuario
     *
     * @param correo Usuario a eliminar
     * @throws UsuarioNoEncontradoExcepcion Si el ususario no existe
     */
    // Kevin Jimenez
    @Override
    @Transactional
    public void eliminar(String correo) throws UsuarioNoEncontradoExcepcion {
        if(usuariosRepositorio.existsById(correo)) {
            usuariosRepositorio.deleteById(correo);
        }else{
            throw new UsuarioNoEncontradoExcepcion("El usuario no existe.", HttpStatus.NOT_FOUND, System.currentTimeMillis());
        }
    }

    @Override
    public void cambiarEstado(String correo, Boolean estadoNuevo) throws UsuarioNoEncontradoExcepcion {
        if(usuariosRepositorio.existsById(correo)) {
            UsuarioEntidad usuario =  usuariosRepositorio.findById(correo).get();
            usuario.setValid(estadoNuevo);
            usuariosRepositorio.save(usuario);
        }else{
            throw new UsuarioNoEncontradoExcepcion("El usuario no existe.", HttpStatus.NOT_FOUND, System.currentTimeMillis());
        }
    }
}
