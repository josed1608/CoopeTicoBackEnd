/***
 * Interface del servicio de tokens de recuperacion de contraseña
 */

package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.bd.TokenRecuperacionContrasenaEntidad;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.Email;

/***
 * Interface del servicio de tokens de recuperacion de contraseña
 */
@Validated
public interface TokensRecuperacionContrasenaServicio {
    /***
     * Devuelve un token para recuperar una contraseña
     * @param correo Identificador del usuario
     * @return Token para recuperar una contraseña
     */
    TokenRecuperacionContrasenaEntidad getToken(String correo);

    /***
     * Elimina el token perteneciente al usuario [correo}
     * @param correo Identificador del usuario
     */
    void eliminarToken(String correo);

    /***
     * Crea e inserta un nuevo token en la base de datos
     * @param correo Identificador del usuario
     * @return El token generado, en caso de fallo devuelve null
     */
    String insertarToken(@Email String correo);
}
