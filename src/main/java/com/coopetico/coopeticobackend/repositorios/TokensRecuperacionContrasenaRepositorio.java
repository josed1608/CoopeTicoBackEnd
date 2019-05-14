package com.coopetico.coopeticobackend.repositorios;

import com.coopetico.coopeticobackend.entidades.bd.TokenRecuperacionContrasenaEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TokensRecuperacionContrasenaRepositorio extends JpaRepository<TokenRecuperacionContrasenaEntidad, String> {


    // @Query("select t.fkCorreoUsuario, t.fechaExpiracion, t.token from TokenRecuperacionContrasenaEntidad t where t.token = token")
    TokenRecuperacionContrasenaEntidad findByFkCorreoUsuario(String correo);


    @Query("delete from TokenRecuperacionContrasenaEntidad t where t.token = token")
    int deleteByToken(@Param("token") String token);

}
