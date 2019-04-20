package com.coopetico.coopeticobackend.servicios;
//Programador: TODO
//Fecha: 04/04/2019
//Version: 0.01
//Implementacion de la interfaz del Servicio de Permiso-Grupo.


import com.coopetico.coopeticobackend.entidades.TokenRecuperacionContrasenaEntidad;
import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.repositorios.GruposRepositorio;
import com.coopetico.coopeticobackend.repositorios.TokensRecuperacionContrasenaRepositorio;
import com.coopetico.coopeticobackend.repositorios.UsuariosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import java.util.UUID;

@Validated
@Service
public class TokensRecuperacionContrasenaServicioImpl implements TokensRecuperacionContrasenaServicio {

    private final UsuariosRepositorio usuariosRepositorio;

    @Autowired
    public TokensRecuperacionContrasenaServicioImpl(UsuariosRepositorio usuariosRepositorio) {
        this.usuariosRepositorio = usuariosRepositorio;
    }

    @Autowired
    TokensRecuperacionContrasenaRepositorio tokensRepo;

    @Override
    public TokenRecuperacionContrasenaEntidad getToken(String correo) {
       return  tokensRepo.findByFkCorreoUsuario(correo);
    }

    @Override
    public void eliminarToken(String correo) {
        tokensRepo.deleteById(correo);
    }

    @Override
    public String insertarToken(@Email String correo) {
        String token = UUID.randomUUID().toString();
        TokenRecuperacionContrasenaEntidad tokenEntidad = new TokenRecuperacionContrasenaEntidad();
        if( usuariosRepositorio.findById(correo).orElse(null) != null){
            tokenEntidad.setFkCorreoUsuario(correo);
            tokenEntidad.setToken(token);
            if (tokensRepo.save(tokenEntidad)  == null){
                return null;
            }
            return token;
        }
        return null;
    }
}
