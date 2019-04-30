package com.coopetico.coopeticobackend.servicios.integration;

/**
 Test de integracion del PermisoServicio
 @author      Jefferson Alvarez
 @since       18-04-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.entidades.PermisoEntidad;
import com.coopetico.coopeticobackend.repositorios.PermisosRepositorio;
import com.coopetico.coopeticobackend.servicios.PermisosServicio;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class PermisoServicioIntegrationTest {

    @Autowired
    PermisosServicio permisosServicio;

    @Autowired
    PermisosRepositorio permisosRepositorio;

    @Before
    public void setup() {
        permisosRepositorio.deleteAll();
    }

    @Test
    @Transactional
    public void obtenerPermisos() throws Exception {
        //IMPORTANTE
        //Las pruebas se hacen con la tabla de permisos vacia
        PermisoEntidad permisoEntidad = new PermisoEntidad(100, "Perdir viaje", null);
        permisosRepositorio.save(permisoEntidad);

        PermisoEntidad permisoEntidad2 = new PermisoEntidad(101, "Ver taxis cerca", null);
        permisosRepositorio.save(permisoEntidad2);

        //Act
        List<PermisoEntidad> permisos = permisosServicio.getPermisos();

        //Assert
        //Las pruebas se hacen con la tabla de permisos vacia
        assertTrue(permisos != null);
        //assertTrue(permisos.size() == 2);
        assertTrue(permisos.size() > 0);
    }

    @Test
    @Transactional
    public void obtenerPermiso() throws Exception {
        //IMPORTANTE
        //Las pruebas se hacen con la tabla de permisos vacia
        PermisoEntidad permisoEntidad = new PermisoEntidad(100, "Perdir viaje",null);
        permisosRepositorio.save(permisoEntidad);

        //Act
        PermisoEntidad permiso = permisosServicio.getPermisoPorPK(100);

        //Assert
        assertNotNull(permiso);
        assertTrue(permiso.getPkId() == permisoEntidad.getPkId());
    }
}
