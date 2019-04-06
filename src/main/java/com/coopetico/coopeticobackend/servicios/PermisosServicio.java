package com.coopetico.coopeticobackend.servicios;

//Programador: Jefferson Alvarez
//Fecha: 04/04/2019
//Version: 0.01
//Interfaz del Servicio de Permiso.

import com.coopetico.coopeticobackend.entidades.PermisoEntidad;
import java.util.List;

public interface PermisosServicio {

    public List<PermisoEntidad> getPermisos();

}
