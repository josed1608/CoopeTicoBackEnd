package com.coopetico.coopeticobackend.controladores;
//Programador: Jefferson Alvarez
//Fecha: 04/04/2019
//Version: 0.1
//Controlador de la entidad Permisos que contiene los permisos existentes en el sistema.

import com.coopetico.coopeticobackend.entidades.PermisoEntidad;
import com.coopetico.coopeticobackend.repositorios.PermisosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/permisos")
public class PermisoEntidadControlador {

    @Autowired
    PermisosRepositorio permisosRepo;

    @GetMapping("/listarPermisos")
    public List<PermisoEntidad> getPermisos(){
        return permisosRepo.findAll();
    }

}
