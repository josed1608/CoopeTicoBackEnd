package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;

import java.util.Optional;

public interface UsuarioServicio {
    /**
     * Introduce un usuario a la base de datos
     *
     * @param usuarioSinGrupo Entidad del usuario sin la referencia al grupo
     * @param grupoId id del grupo de permisos al que se va a linkear el usuario
     */
    void agregarUsuario(UsuarioEntidad usuarioSinGrupo, String grupoId);

    /**
     * Encuentra un usuario por correo
     *
     * @param correo correo del usuario que se va a buscar
     * @return retorna el usuario en un Optional para poder aplicar lógica en caso de que no lo encuentre
     */
    Optional<UsuarioEntidad> usuarioPorCorreo(String correo);
}
