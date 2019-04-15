package com.coopetico.coopeticobackend.servicios;

/**
 Implementacion de la interfaz del Servicio de Permiso.
 @author      Jefferson Alvarez
 @since       4-04-2019
 @version:    1.0
 */


import com.coopetico.coopeticobackend.entidades.PermisoEntidad;
import com.coopetico.coopeticobackend.repositorios.PermisosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermisosServicioImpl implements PermisosServicio {


    private final PermisosRepositorio permisosRepo;

    @Autowired
    PermisosServicioImpl (PermisosRepositorio permisosRepo){
        this.permisosRepo = permisosRepo;
    }

     /**
     * Metodo que obtiene los permisos existentes del sistema
     * @return Lista de permisos con el ID y la Descripcion
     */
    public List<PermisoEntidad> getPermisos(){
        List<PermisoEntidad> lista = permisosRepo.getPermisoIDyDescripcion();
        return lista;
    }

     /**
     * Metodo que obtiene un objeto de la entidad Permiso
     * @param permisoPK Llave primaria del objeto de interes
     * @return Objeto de la entidad permiso
     */
    public PermisoEntidad getPermisoPorPK(int permisoPK){
        Optional<PermisoEntidad> permisoEntidad = permisosRepo.findById(permisoPK);
        return permisoEntidad.get();
    }
}