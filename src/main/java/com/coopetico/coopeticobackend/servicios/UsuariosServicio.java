/**
 * Autor:
 * (1) Joseph Rementería (b55824).
 *
 * Fecha: 06/04/2019.
 *
 * Esta es la interfaz para el servicio que maneja la comunicación
 * hacia/desde la especialización Cliente del ISA Usuario.
 *
 */
package com.coopetico.coopeticobackend.servicios;
import com.coopetico.coopeticobackend.entidades.ClienteEntidad;
import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;

//import java.util.List;

public interface UsuariosServicio {

    // List<UsuarioEntidad> consultar();

    // UsuarioEntidad guardar(UsuarioEntidad taxista);

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

    ClienteEntidad consultarClientePorId (String corre);

    // void eliminar(String correo);

}
