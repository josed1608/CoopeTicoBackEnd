package com.coopetico.coopeticobackend.controladores;

import com.coopetico.coopeticobackend.entidades.PermisoEntidad;
import com.coopetico.coopeticobackend.repositorios.PermisosGruposRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.coopetico.coopeticobackend.entidades.PermisosGrupoEntidad;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PermisoGrupoEntidadControlador {

    @Autowired
    PermisosGruposRepositorio repoPermisos;


    @GetMapping("/permisos")
    public List<PermisosGrupoEntidad> getPermisos(){

        return repoPermisos.findAll();

    }
}
