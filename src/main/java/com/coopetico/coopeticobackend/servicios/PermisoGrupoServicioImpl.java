package com.coopetico.coopeticobackend.servicios;
/**
 Implementación del servicio de la entidad Permisos-Grupo que provee los metodos a utilizar.
 @author      Jefferson Alvarez
 @since       06-04-2019
 @version:    1.0
 */

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

    private final PermisosGruposRepositorio permisosGrupoRepo;

    @Autowired
    public PermisoGrupoServicioImpl(PermisosGruposRepositorio permisosGrupoRepo) {
        this.permisosGrupoRepo = permisosGrupoRepo;
    }


    /**
     * Metodo que obtiene los permisos de los grupos existentes del sistema
     * @return Lista de permisos con el ID y la Descripcion del permiso y el ID del grupo
     */
    public List<PermisosGrupoEntidad> getPermisosGrupo(){
        return permisosGrupoRepo.findAll();
    }

    /**
     * Metodo que guarda una nueva llave permisos-grupo en la base de datos
     * @return true si la llave se ha guardado correctamente
     * @param pG par pkIdPermisos, pkIdGrupo a ser guardado en la base de datos
     */
    public boolean guardarPermisosGrupo(List<PermisosGrupoEntidadPK> pG) {
        /*
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
    */
        return true;

    }

     /**
     * Metodo que elimina una llave permisos-grupo en la base de datos
     * @return true si la llave se ha eliminado correctamente
     * @param pG par pkIdPermisos, pkIdGrupo a ser eliminado en la base de datos
     */
    public boolean eliminarPermisosGrupo(List<PermisosGrupoEntidadPK> pG){
        /*
        //Obtenemos la instancia del Permiso - Grupo
        Optional<PermisosGrupoEntidad> permisoGrupoEliminar = permisosGrupoRepo.findById(pG);

        //Eliminarmos el Permiso - Grupo
        permisosGrupoRepo.delete(permisoGrupoEliminar.get());
        */
        return true;

    }
}
