package com.coopetico.coopeticobackend.servicios;

/**
 Implementacion de la interfaz del Servicio de Permiso.
 @author      Jefferson Alvarez
 @since       4-04-2019
 @version:    1.0
 */


import com.coopetico.coopeticobackend.entidades.PermisoEntidad;
import com.coopetico.coopeticobackend.excepciones.PermisoNoExisteExcepcion;
import com.coopetico.coopeticobackend.repositorios.PermisosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PermisosServicioImpl implements PermisosServicio {


    private final PermisosRepositorio permisosRepo;

    @Autowired
    public PermisosServicioImpl (PermisosRepositorio permisosRepo){
        this.permisosRepo = permisosRepo;
    }

     /**
     * Metodo que obtiene los permisos existentes del sistema
     * @return Lista de permisos con el ID y la Descripcion
     */
     @Override
     @Transactional (readOnly = true)
    public List<PermisoEntidad> getPermisos(){
        List<PermisoEntidad> lista = permisosRepo.getPermisoIDyDescripcion();
        return lista;
    }

     /**
     * Metodo que obtiene un objeto de la entidad Permiso
     * @param permisoPK Llave primaria del objeto de interes
     * @return Objeto de la entidad permiso
     * @throws PermisoNoExisteExcepcion si el permiso no existe
     */
     @Override
     @Transactional(readOnly = true)
    public PermisoEntidad getPermisoPorPK(int permisoPK){
        PermisoEntidad permisoEntidad = permisosRepo.findById(permisoPK)
                .orElseThrow(() -> new PermisoNoExisteExcepcion("Permiso "
                        + permisoPK
                        +" no existe", HttpStatus.BAD_REQUEST, System.currentTimeMillis()));
        return permisoEntidad;
    }
}