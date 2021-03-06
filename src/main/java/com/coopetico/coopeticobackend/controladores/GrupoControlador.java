package com.coopetico.coopeticobackend.controladores;

/**
 Controlador de la entidad Grupo que contiene los grupos existentes en el sistema.
 @author      Jefferson Alvarez
 @since       13-04-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.entidades.bd.GrupoEntidad;
import com.coopetico.coopeticobackend.servicios.GrupoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/grupos")
public class GrupoControlador {

    private final GrupoServicio grupoServicio;

    @Autowired
    GrupoControlador (GrupoServicio grupoServicio){
        this.grupoServicio = grupoServicio;
    }

    /**
     * Metodo que obtiene los grupos existentes del sistema
     * @return Lista de permisos con el ID
     */
    @GetMapping()
    public List<GrupoEntidad> getGrupos(){
        List<GrupoEntidad> listaGrupos = grupoServicio.getGrupos();
        return  listaGrupos;
    }

}
