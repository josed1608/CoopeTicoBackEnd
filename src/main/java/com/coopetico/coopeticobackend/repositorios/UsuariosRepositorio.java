package com.coopetico.coopeticobackend.repositorios;

import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariosRepositorio extends JpaRepository<UsuarioEntidad, String> {

}
