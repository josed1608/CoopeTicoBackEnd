package com.coopetico.coopeticobackend.repositorios;

import com.coopetico.coopeticobackend.entidades.bd.ClienteEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientesRepositorio extends JpaRepository<ClienteEntidad, String> {

}
