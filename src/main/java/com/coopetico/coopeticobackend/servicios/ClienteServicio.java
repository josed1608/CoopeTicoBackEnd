package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;

public interface ClienteServicio {
    /**
     * Agrega un cliente a la base de datos con referencia al usuario que se le da como parámetro
     *
     * @param usuarioEntidad usuario al que se linkea el cliente
     */
    void agregarCliente(UsuarioEntidad usuarioEntidad);
}
