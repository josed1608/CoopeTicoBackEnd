package com.coopetico.coopeticobackend.servicios;
//Programador: Jefferson Alvarez
//Fecha: 04/04/2019
//Version: 0.01
//Implementacion de la interfaz del Servicio de Permiso-Grupo.


import com.coopetico.coopeticobackend.entidades.GrupoEntidad;
import com.coopetico.coopeticobackend.entidades.PermisoEntidad;
import com.coopetico.coopeticobackend.entidades.PermisosGrupoEntidad;
import com.coopetico.coopeticobackend.entidades.PermisosGrupoEntidadPK;
import com.coopetico.coopeticobackend.repositorios.GruposRepositorio;
import com.coopetico.coopeticobackend.repositorios.PermisosGruposRepositorio;
import com.coopetico.coopeticobackend.repositorios.PermisosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermisoGrupoServicioImpl implements PermisoGrupoServicio {

    //Necesitamos el repostitorio de Permisos-Grupo
    @Autowired
    PermisosGruposRepositorio permisosGrupoRepo;

    //Necesitamos el repostitorio de Permisos
    @Autowired
    PermisosRepositorio permisosRepo;

    //Necesitamos el repostitorio de Grupo
    @Autowired
    GruposRepositorio gruposRepo;


    public List<PermisosGrupoEntidad> getPermisosGrupo(){
        return permisosGrupoRepo.findAll();
    }

    public void guardarPermisosGrupo(PermisosGrupoEntidadPK pG) {
        //Creamos la entidad a insertar
        PermisosGrupoEntidad permisoGrupoInsertar = new PermisosGrupoEntidad();

        //Para insertar la entidad ocupamos la entidad Permiso
        int permisoID = pG.getPkIdPermisos();
        Optional<PermisoEntidad> permiso = permisosRepo.findById(permisoID);

        //Para insertar la entidad ocupamos la entidad del Grupo
        String grupoID = pG.getPkIdGrupo();
        Optional<GrupoEntidad> grupo = gruposRepo.findById(grupoID);

        //Insertamos los atributos de la entidad a insertar
        permisoGrupoInsertar.setPermisosGrupoEntidadPK(pG);
        permisoGrupoInsertar.setGrupoByPkIdGrupo(grupo.get());
        permisoGrupoInsertar.setPermisoByPkIdPermisos(permiso.get());

        //Guardamos en la base
        permisosGrupoRepo.save(permisoGrupoInsertar);

    }

    public void eliminarPermisosGrupo(PermisosGrupoEntidadPK pG){
        //Obtenemos la instancia del Permiso - Grupo
        Optional<PermisosGrupoEntidad> permisoGrupoEliminar = permisosGrupoRepo.findById(pG);

        //Eliminarmos el Permiso - Grupo
        permisosGrupoRepo.delete(permisoGrupoEliminar.get());

    }
}
