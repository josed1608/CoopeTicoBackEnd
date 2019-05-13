package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.bd.GrupoEntidad;
import com.coopetico.coopeticobackend.entidades.bd.UsuarioEntidad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UsuarioServicio {
    /**
     * Introduce un usuario a la base de datos
     *
     * @param usuarioSinGrupo Entidad del usuario sin la referencia al grupo
     * @param grupoId id del grupo de permisos al que se va a linkear el usuario
     * @return Retorna el usuario agregado
     */
    UsuarioEntidad agregarUsuario(UsuarioEntidad usuarioSinGrupo, String grupoId);


    /**
     * Metodo para agregar un usuario a la BD
     * @param usuarioEntidad Usuario a agregar
     * @return Nuevo usuario creado
     */
    UsuarioEntidad crearUsuario(UsuarioEntidad usuarioEntidad);

    /**
     * Encuentra un usuario por correo
     *
     * @param correo correo del usuario que se va a buscar
     * @return retorna el usuario en un Optional para poder aplicar lógica en caso de que no lo encuentre
     */
    Optional<UsuarioEntidad> usuarioPorCorreo(String correo);

    /**
     * Encuentra los roles o grupos de permisos del usuario dado
     * @param usuario usuario entidad a la que se le encontraran los roles
     * @return Lista de Strings con los permisos del usuario
     */
    List<String> obtenerPermisos(UsuarioEntidad usuario);


    /**
     * Permite obtener todos los usuarios
     * @return Retorna una lista de usuarios
     */
    List<UsuarioEntidad> obtenerUsuarios();


    /**
     * Metodo para realizar la paginacion de los datos
     * @param pageable Cantidad de elementos a recuperar y el numero de la pagina
     * @return Elementos de la pagina
     */
    Page<UsuarioEntidad> obtenerUsuarios(Pageable pageable);

    /**
     * Permite obtener todos los usuarios de un grupo
     * @return Retorna la lista de usuarios que pertenecen al grupo
     */
    List<UsuarioEntidad> obtenerUsuariosPorGrupo(GrupoEntidad grupo);


    /**
     * Elimina un usuario
     * @param correo Usuario a eliminar
     */
    void eliminar(String correo);

    /**
     * Devuelve si el usuario es cliente, taxista o operador
     * @param usuario entidad usuario base
     * @return String con el tipo, puede ser "cliente", "taxista" o "operador"
     */
    String obtenerTipo(UsuarioEntidad usuario);
}
