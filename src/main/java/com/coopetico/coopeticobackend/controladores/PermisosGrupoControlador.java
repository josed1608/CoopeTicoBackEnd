package com.coopetico.coopeticobackend.controladores;

/**
 Controlador de la entidad Permisos_Grupo que contiene los permisos de cada grupo existentes en el sistema.
 @author      Jefferson Alvarez
 @since       06-04-2019
 @version:    1.0
 */


import com.coopetico.coopeticobackend.entidades.PermisosGrupoEntidad;
import com.coopetico.coopeticobackend.entidades.PermisosGrupoEntidadPK;
import com.coopetico.coopeticobackend.servicios.PermisoGrupoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permisosGrupo")
public class PermisosGrupoControlador {

    @Autowired
    PermisoGrupoServicio permisosGrupo;

    @GetMapping()
    public List<PermisosGrupoEntidad> getPermisosGrupo(){
        List<PermisosGrupoEntidad> lista = permisosGrupo.getPermisosGrupo();
        return lista;
    }

    @PostMapping()
    public void guardarPermisoGrupo(@RequestBody List<PermisosGrupoEntidadPK> pG) {
        permisosGrupo.guardarPermisosGrupo(pG);
    }

    @DeleteMapping()
    public void eliminarPermisoGrupo(@RequestBody List<PermisosGrupoEntidadPK> pG) {
        permisosGrupo.eliminarPermisosGrupo(pG);
    }
}
