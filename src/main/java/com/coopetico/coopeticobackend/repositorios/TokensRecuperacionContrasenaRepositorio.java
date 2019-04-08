package com.coopetico.coopeticobackend.repositorios;

import com.coopetico.coopeticobackend.entidades.TokenRecuperacionContrasenaEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokensRecuperacionContrasenaRepositorio extends JpaRepository<TokenRecuperacionContrasenaEntidad, String> {
}
