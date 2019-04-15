package com.coopetico.coopeticobackend.servicios;

/**
 Implementacion del servicio de la entidad Grupo.
 @author      Jefferson Alvarez
 @since       13-04-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.entidades.GrupoEntidad;
import com.coopetico.coopeticobackend.repositorios.GruposRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrupoServicioImpl implements GrupoServicio{

    private final GruposRepositorio grupoRepo;

    @Autowired
    GrupoServicioImpl (GruposRepositorio grupoRepo){
        this.grupoRepo = grupoRepo;
    }

    /**
     * Metodo que obtiene los grupos existentes del sistema
     * @return Lista de grupos con el ID
     */
    public List<GrupoEntidad> getGrupos(){
        List<GrupoEntidad> listaGrupos = grupoRepo.getIDGrupos();
        return  listaGrupos;
    }

}
