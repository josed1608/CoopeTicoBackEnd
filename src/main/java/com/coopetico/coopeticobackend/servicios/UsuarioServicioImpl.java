/***
 * Implementacion del servicio de Usuarios.
 */

package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.UsuarioTemporal;
import com.coopetico.coopeticobackend.entidades.bd.GrupoEntidad;
import com.coopetico.coopeticobackend.entidades.bd.PermisosGrupoEntidad;
import com.coopetico.coopeticobackend.entidades.bd.UsuarioEntidad;
import com.coopetico.coopeticobackend.excepciones.CorreoTomadoExcepcion;
import com.coopetico.coopeticobackend.excepciones.GrupoNoExisteExcepcion;
import com.coopetico.coopeticobackend.excepciones.UsuarioNoEncontradoExcepcion;
import com.coopetico.coopeticobackend.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServicioImpl implements UsuarioServicio{
    private final UsuariosRepositorio usuariosRepositorio;
    private final ClientesRepositorio clientesRepositorio;
    private final TaxistasRepositorio  taxistasRepositorio;
    private final OperadorRepositorio operadorRepositorio;
    private final GruposRepositorio gruposRepositorio;
    private final PasswordEncoder encoder;

    //En caso de que no se haya asignado contrasena
    private final String CONTRASENA_DEFAULT = "aguatico123";
    @Autowired
    public UsuarioServicioImpl(UsuariosRepositorio usuariosRepositorio, ClientesRepositorio clientesRepositorio, TaxistasRepositorio taxistasRepositorio, OperadorRepositorio operadorRepositorio, GruposRepositorio gruposRepositorio, PasswordEncoder encoder) {
        this.usuariosRepositorio = usuariosRepositorio;
        this.clientesRepositorio = clientesRepositorio;
        this.taxistasRepositorio = taxistasRepositorio;
        this.operadorRepositorio = operadorRepositorio;
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
    public String obtenerTipo(UsuarioEntidad usuario) {
        String tipo = "";
        if (clientesRepositorio.findById(usuario.getPkCorreo()).isPresent())
            tipo = "cliente";
        if (taxistasRepositorio.findById(usuario.getPkCorreo()).isPresent())
            tipo = "taxista";
        if (operadorRepositorio.findById(usuario.getPkCorreo()).isPresent())
            tipo = "operador";
        return tipo;
    }

    /**
     * Cambia el estado de un usuario
     *
     * Habilita o deshabilita un usario dependiendo del [nuevoEstado] proporcionado.
     *
     * @param correo Correo de usuario a modificar
     * @param estadoNuevo Estado que se le dara al usuario
     * @author Kevin Jimenez
     */
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

    @Override
    public UsuarioEntidad modificarUsuario(UsuarioEntidad usuarioNuevo, String correo) throws UsuarioNoEncontradoExcepcion {
        if (usuariosRepositorio.findById(correo).isPresent()) {
            if (!usuarioNuevo.equals(usuarioPorCorreo(correo))) {
                usuarioNuevo.setContrasena(encoder.encode(usuarioNuevo.getContrasena()));
                if (correo == usuarioNuevo.getPkCorreo()) {
                    return usuariosRepositorio.save(usuarioNuevo);
                }
                usuariosRepositorio.deleteById(correo);
                return usuariosRepositorio.save(usuarioNuevo);
            } else {
                return usuarioNuevo;
            }
        } else {
            throw new UsuarioNoEncontradoExcepcion("El usuario no existe.", HttpStatus.NOT_FOUND, System.currentTimeMillis());
        }
    }
}
