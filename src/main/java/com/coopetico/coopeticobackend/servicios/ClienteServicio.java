package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;

public interface ClienteServicio {
    /**
     * Agrega un cliente a la base de datos con referencia al usuario que se le da como parámetro
     *
     * @param usuarioEntidad usuario al que se linkea el cliente
     */
    void agregarCliente(UsuarioEntidad usuarioEntidad);

    /**
     * Borra al cliente y el usuario asociado de la base de datos
     * @param pkCorreo correo del usuario a borrar
     */
    void borrarCliente(String pkCorreo);
}
