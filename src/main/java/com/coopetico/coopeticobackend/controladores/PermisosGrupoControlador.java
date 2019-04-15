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
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping()
    public List<PermisosGrupoEntidad> getPermisosGrupo(@RequestBody GrupoEntidad grupo){
        List<PermisosGrupoEntidad> lista = permisosGrupoServicio.getPermisosGrupo(grupo);
        return lista;
    }

    @PostMapping()
    public void guardarPermisoGrupo(@RequestBody List<PermisosGrupoEntidadPK> pG) {

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
    }

    @DeleteMapping()
    public void eliminarPermisoGrupo(@RequestBody List<PermisosGrupoEntidadPK> pG) {
        //permisosGrupoServicio.eliminarPermisosGrupo(pG);
    }
}
