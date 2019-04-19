package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.excepciones.UsuarioNoEncontradoExcepcion;

public interface ClienteServicio {
    /**
     * Agrega un cliente a la base de datos con referencia al usuario que se le da como parámetro
     *
     * @param usuarioEntidad usuario al que se linkea el cliente
     * @throws UsuarioNoEncontradoExcepcion cuando el usuario que se da para asociar el cliente no existe
     */
    void agregarCliente(UsuarioEntidad usuarioEntidad) throws UsuarioNoEncontradoExcepcion;

    /**
     * Borra al cliente y el usuario asociado de la base de datos
     * @param pkCorreo correo del usuario a borrar
     * @throws UsuarioNoEncontradoExcepcion si el usuario que se desea borrar no existe
     */
    void borrarCliente(String pkCorreo) throws  UsuarioNoEncontradoExcepcion;
}
