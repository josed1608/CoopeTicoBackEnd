/***
 * Interface del servicio de tokens de recuperacion de contraseña
 */
package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.TokenRecuperacionContrasenaEntidad;
import com.coopetico.coopeticobackend.repositorios.TokensRecuperacionContrasenaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.Email;
import java.util.UUID;


/***
 * Interface del servicio de tokens de recuperacion de contraseña
 */
@Validated
@Service
public class TokensRecuperacionContrasenaServicioImpl implements TokensRecuperacionContrasenaServicio {

    @Autowired
    TokensRecuperacionContrasenaRepositorio tokensRepo;

    // Kevin Jimenez
    /***
     * Devuelve un token para recuperar una contraseña
     * @param correo Identificador del usuario
     * @return Token para recuperar una contraseña
     */
    @Override
    public TokenRecuperacionContrasenaEntidad getToken(String correo) {
       return  tokensRepo.findByFkCorreoUsuario(correo);
    }

    /***
     * Elimina el token perteneciente al usuario [correo}
     * @param correo Identificador del usuario
     */
    @Override
    public void eliminarToken(String correo) {
        tokensRepo.deleteById(correo);
    }

    // Kevin Jimenez
    /***
     * Crea e inserta un nuevo token en la base de datos
     * @param correo Identificador del usuario
     * @return El token generado, en caso de fallo devuelve null
     */
    @Override
    public String insertarToken(@Email String correo) {
        String token = UUID.randomUUID().toString();
        TokenRecuperacionContrasenaEntidad tokenEntidad = new TokenRecuperacionContrasenaEntidad();
        tokenEntidad.setFkCorreoUsuario(correo);
        tokenEntidad.setToken(token);
        if (tokensRepo.save(tokenEntidad)  == null){
            return null;
        }
        return token;
    }
}
