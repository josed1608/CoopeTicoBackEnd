package com.coopetico.coopeticobackend.servicios;

/**
 Implementacion del servicio de la entidad Grupo.
 @author      Jefferson Alvarez
 @since       13-04-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.entidades.GrupoEntidad;
import com.coopetico.coopeticobackend.excepciones.GrupoNoExisteExcepcion;
import com.coopetico.coopeticobackend.repositorios.GruposRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     @Override
     @Transactional (readOnly = true)
    public List<GrupoEntidad> getGrupos(){
        List<GrupoEntidad> listaGrupos = grupoRepo.getIDGrupos();
        return  listaGrupos;
    }

     /**
     * Metodo que obtiene un objeto de la entidad Grupo
     * @param grupoPK Llave primaria del objeto de interes
     * @return Objeto de la entidad Grupo
     * @throws GrupoNoExisteExcepcion si el grupo no existe
     */
     @Override
     @Transactional(readOnly = true)
    public GrupoEntidad getGrupoPorPK(String grupoPK){
        GrupoEntidad grupoEntidad = grupoRepo.findById(grupoPK)
                .orElseThrow(() -> new GrupoNoExisteExcepcion("Grupo "
                        + grupoPK
                        +" no existe", HttpStatus.BAD_REQUEST, System.currentTimeMillis()));
        return grupoEntidad;
    }
}
