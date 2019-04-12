package com.coopetico.coopeticobackend.servicios;
//Programador: TODO
//Fecha: 04/04/2019
//Version: 0.01
//Implementacion de la interfaz del Servicio de Permiso-Grupo.


import com.coopetico.coopeticobackend.entidades.TokenRecuperacionContrasenaEntidad;
import com.coopetico.coopeticobackend.repositorios.TokensRecuperacionContrasenaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import java.util.UUID;

@Validated
@Service
public class TokensRecuperacionContrasenaServicioImpl implements TokensRecuperacionContrasenaServicio {

    @Autowired
    TokensRecuperacionContrasenaRepositorio tokensRepo;

    @Override
    public TokenRecuperacionContrasenaEntidad getToken(String correo) {
        return tokensRepo.getOne(correo);
    }

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
