package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;

import java.util.Optional;

public interface UsuarioServicio {
    void agregarUsuario(UsuarioEntidad usuarioSinGrupo, String grupoId);
    Optional<UsuarioEntidad> usuarioPorCorreo(String correo);
}
