package com.coopetico.coopeticobackend.servicios;
//Programador: Jefferson Alvarez
//Fecha: 04/04/2019
//Version: 0.01
//Implementacion de la interfaz del Servicio de Permiso-Grupo.


import com.coopetico.coopeticobackend.entidades.PermisosGrupoEntidad;
import com.coopetico.coopeticobackend.repositorios.PermisosGruposRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermisoGrupoServicioImpl implements PermisoGrupoServicio {

    @Autowired
    PermisosGruposRepositorio permisosGrupoRepo;

    public List<PermisosGrupoEntidad> getPermisosGrupo(){
        return permisosGrupoRepo.findAll();
    }
}
