package com.coopetico.coopeticobackend.controladores;

/**
   Controlador de la entidad Permisos que contiene los permisos existentes en el sistema.
   @author      Jefferson Alvarez
   @since       4-04-2019
   @version:    1.0
*/

import com.coopetico.coopeticobackend.entidades.bd.PermisoEntidad;
import com.coopetico.coopeticobackend.servicios.PermisosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/permisos")
public class PermisoControlador {
    
    private final PermisosServicio permisos;

    @Autowired
    public PermisoControlador(PermisosServicio permisos) {
        this.permisos = permisos;
    }

     /**
     * Metodo que obtiene los permisos existentes del sistema
     * @return Lista de permisos con el ID y la Descripcion
     */
    @GetMapping()
    @CrossOrigin(origins = "http://localhost:4200")
    @PreAuthorize("hasAuthority('404')")
    public List<PermisoEntidad> getPermisos(){
        List<PermisoEntidad> lista = permisos.getPermisos();
        return lista;
    }

}
