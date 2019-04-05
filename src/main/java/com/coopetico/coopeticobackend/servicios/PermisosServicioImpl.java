package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.PermisoEntidad;
import com.coopetico.coopeticobackend.repositorios.PermisosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermisosServicioImpl implements PermisosServicio {

    @Autowired
    PermisosRepositorio permisosRepo;

    public List<PermisoEntidad> getPermisos(){
        List<PermisoEntidad> lista = permisosRepo.findAll();
        return lista;
    }

}