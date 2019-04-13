package com.coopetico.coopeticobackend.controladores;
//Programador: Jefferson Alvarez
//Fecha: 04/04/2019
//Version: 0.01
//Controlador de la entidad Permisos_Grupo que contiene los permisos de cada grupo existentes en el sistema.

import com.coopetico.coopeticobackend.entidades.PermisosGrupoEntidad;
import com.coopetico.coopeticobackend.entidades.PermisosGrupoEntidadPK;
import com.coopetico.coopeticobackend.servicios.PermisoGrupoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permisosGrupo")
public class PermisosGrupoEntidadControlador {

    @Autowired
    PermisoGrupoServicio permisosGrupo;

    @GetMapping("/listarPermisosGrupo")
    public List<PermisosGrupoEntidad> getPermisosGrupo(){
        List<PermisosGrupoEntidad> lista = permisosGrupo.getPermisosGrupo();
        return lista;
    }

    @PostMapping("/listarPermisosGrupo")
    public void guardarPermisoGrupo(@RequestBody PermisosGrupoEntidadPK pG) {
        permisosGrupo.guardarPermisosGrupo(pG);
    }

    @DeleteMapping("/listarPermisosGrupo")
    public void eliminarPermisoGrupo(@RequestBody PermisosGrupoEntidadPK pG) {
        permisosGrupo.eliminarPermisosGrupo(pG);
    }
}
