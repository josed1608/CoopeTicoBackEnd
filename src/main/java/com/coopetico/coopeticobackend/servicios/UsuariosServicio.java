package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;

//import java.util.List;

public interface UsuariosServicio {

    // List<UsuarioEntidad> consultar();

    // UsuarioEntidad guardar(UsuarioEntidad taxista);

    UsuarioEntidad consultarPorId(String correo);

    // void eliminar(String correo);

}
