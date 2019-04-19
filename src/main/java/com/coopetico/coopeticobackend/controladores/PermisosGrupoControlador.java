package com.coopetico.coopeticobackend.controladores;

/**
 Controlador de la entidad Permisos_Grupo que contiene los permisos de cada grupo existentes en el sistema.
 @author      Jefferson Alvarez
 @since       06-04-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.entidades.GrupoEntidad;
import com.coopetico.coopeticobackend.entidades.PermisoEntidad;
import com.coopetico.coopeticobackend.entidades.PermisosGrupoEntidad;
import com.coopetico.coopeticobackend.entidades.PermisosGrupoEntidadPK;
import com.coopetico.coopeticobackend.servicios.GrupoServicio;
import com.coopetico.coopeticobackend.servicios.PermisoGrupoServicio;
import com.coopetico.coopeticobackend.servicios.PermisosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/permisosGrupo")
public class PermisosGrupoControlador {

    private final PermisoGrupoServicio permisosGrupoServicio;

    private final PermisosServicio permisosServicio;

    private final GrupoServicio grupoServicio;

    @Autowired
    PermisosGrupoControlador (PermisoGrupoServicio permisosGrupoServicio, PermisosServicio permisosServicio, GrupoServicio grupoServicio){
        this.permisosGrupoServicio = permisosGrupoServicio;
        this.permisosServicio = permisosServicio;
        this.grupoServicio = grupoServicio;
    }

    /**
     * Metodo que devuelve los permisos del Grupo dado
     * @param id id del grupo
     * @return Lista con los permisos en objetos de PermisosGrupoEntidad
     */
    @GetMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<PermisoEntidad> getPermisosGrupo(@PathVariable String id){
        GrupoEntidad grupoEntidad = grupoServicio.getGrupoPorPK(id);
        List<PermisoEntidad> listaPermisosGrupo = permisosGrupoServicio.getPermisosGrupo(grupoEntidad);
        return listaPermisosGrupo;
    }

    /**
     * Metodo que devuelve los permisos que no tiene el Grupo dado
     * @param id id del grupo
     * @return Lista con los permisos en objetos de PermisosGrupoEntidad
     */
    @GetMapping("/-{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<PermisoEntidad> getNoPermisosGrupo(@PathVariable String id){
        GrupoEntidad grupoEntidad = grupoServicio.getGrupoPorPK(id);
        List<PermisoEntidad> listaPermisos = permisosServicio.getPermisos();
        List<PermisoEntidad> listaPermisosGrupo = permisosGrupoServicio.getPermisosGrupo(grupoEntidad);

        //Aqui quitamos los permisos que tiene un grupo de los permisos generales
        //SOLUCION INICIAL
        listaPermisos.removeAll(listaPermisosGrupo);
        return listaPermisos;
    }

    /**
     * Metodo para guardar llaves permiso-grupo
     * @param pG Lista de objetos que contienen las llaves a guardar
     * @return ok Si las llaves son guardadas correctamente
     */
    @PostMapping()
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity guardarPermisoGrupo(@RequestBody List<PermisosGrupoEntidadPK> pG) {

        for(PermisosGrupoEntidadPK pgEntrante: pG) {
            //Creamos la entidad a insertar
            PermisosGrupoEntidad permisoGrupoInsertar = new PermisosGrupoEntidad();

            //Para insertar la entidad ocupamos la entidad Permiso
            int permisoID = pgEntrante.getPkIdPermisos();
            PermisoEntidad permiso = permisosServicio.getPermisoPorPK(permisoID);

            //Para insertar la entidad ocupamos la entidad del Grupo
            String grupoID = pgEntrante.getPkIdGrupo();
            GrupoEntidad grupo = grupoServicio.getGrupoPorPK(grupoID);

            //Insertamos los atributos de la entidad a insertar
            permisoGrupoInsertar.setPermisosGrupoEntidadPK(pgEntrante);
            permisoGrupoInsertar.setGrupoByPkIdGrupo(grupo);
            permisoGrupoInsertar.setPermisoByPkIdPermisos(permiso);

            //Guardamos el Permiso-Grupo
            permisosGrupoServicio.guardarPermisosGrupo(permisoGrupoInsertar);
        }
        return ok("");
    }

    /**
     * Metodo para eliminar una llave permiso-grupo
     * @param idPermiso id del permiso
     * @param idGrupo id del Grupo
     * @return ok Si la llave es eliminada correctamente
     */
    @DeleteMapping("/{idPermiso}/{idGrupo}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity eliminarPermisoGrupo(@PathVariable("idPermiso")int idPermiso, @PathVariable("idGrupo") String idGrupo) {

        PermisosGrupoEntidadPK entidad = new PermisosGrupoEntidadPK(idPermiso, idGrupo);
        //Obtenemos la instancia del Permiso - Grupo
        PermisosGrupoEntidad permisoGrupoEliminar = permisosGrupoServicio.getPermisoGrupoPorPK(entidad);

        //Eliminarmos el Permiso - Grupo
        permisosGrupoServicio.eliminarPermisosGrupo(permisoGrupoEliminar);

        return ok("");
    }
}
