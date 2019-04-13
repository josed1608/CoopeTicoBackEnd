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
     * Autor: Joseph Rementería (b55824).
     * Fecha: 06/04/2019.
     * <p>
     * Trae una Entidad usuario que corresponde al correo ingresado.
     *
     * @param correo el correo a consultar.
     * @return UsuarioEntidad del correo en la base, null de otra manera.
     */
    UsuarioEntidad consultarPorId(String correo);
}
