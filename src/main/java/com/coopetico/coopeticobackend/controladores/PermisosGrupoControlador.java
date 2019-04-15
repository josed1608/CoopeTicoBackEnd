package com.coopetico.coopeticobackend.controladores;

/**
 Controlador de la entidad Permisos_Grupo que contiene los permisos de cada grupo existentes en el sistema.
 @author      Jefferson Alvarez
 @since       06-04-2019
 @version:    1.0
 */


import com.coopetico.coopeticobackend.entidades.GrupoEntidad;
import com.coopetico.coopeticobackend.entidades.PermisosGrupoEntidad;
import com.coopetico.coopeticobackend.entidades.PermisosGrupoEntidadPK;
import com.coopetico.coopeticobackend.servicios.PermisoGrupoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permisosGrupo")
public class PermisosGrupoControlador {

    private final PermisoGrupoServicio permisosGrupoServicio;

    @Autowired
    PermisosGrupoControlador (PermisoGrupoServicio permisosGrupoServicio){
        this.permisosGrupoServicio = permisosGrupoServicio;
    }

    @GetMapping()
    public List<PermisosGrupoEntidad> getPermisosGrupo(@RequestBody GrupoEntidad grupo){
        List<PermisosGrupoEntidad> lista = permisosGrupoServicio.getPermisosGrupo(grupo);
        return lista;
    }

    @PostMapping()
    public void guardarPermisoGrupo(@RequestBody List<PermisosGrupoEntidadPK> pG) {
        permisosGrupoServicio.guardarPermisosGrupo(pG);
    }

    @DeleteMapping()
    public void eliminarPermisoGrupo(@RequestBody List<PermisosGrupoEntidadPK> pG) {
        permisosGrupoServicio.eliminarPermisosGrupo(pG);
    }
}
