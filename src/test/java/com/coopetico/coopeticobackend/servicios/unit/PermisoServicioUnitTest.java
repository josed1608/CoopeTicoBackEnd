package com.coopetico.coopeticobackend.servicios.unit;

/**
 Test de unidad del PermisoServicio
 @author      Jefferson Alvarez
 @since       18-04-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.entidades.PermisoEntidad;
import com.coopetico.coopeticobackend.repositorios.PermisosRepositorio;
import com.coopetico.coopeticobackend.servicios.PermisosServicioImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PermisoServicioUnitTest {

    @Autowired
    protected WebApplicationContext wac;

    @MockBean
    PermisosRepositorio permisosRepositorio;

    @InjectMocks
    PermisosServicioImpl permisosServicio;

    @Before
    public void setUp() {
        permisosRepositorio = mock(PermisosRepositorio.class);
        permisosServicio = new PermisosServicioImpl(permisosRepositorio);
        MockitoAnnotations.initMocks( this );
    }


    @Test
    public void testObtenerPermisos() throws Exception {

        PermisoEntidad permisoEntidad = new PermisoEntidad();
        permisoEntidad.setPkId(100);

        PermisoEntidad permisoEntidad2 = new PermisoEntidad();
        permisoEntidad2.setPkId(200);

        List<PermisoEntidad> entidades = Arrays.asList(permisoEntidad, permisoEntidad2);
        when(permisosRepositorio.getPermisoIDyDescripcion()).thenReturn(entidades);

        List<PermisoEntidad> entidadesServicio = permisosServicio.getPermisos();

        assertNotNull(entidadesServicio);
        assertTrue(entidadesServicio.size() == 2);
        assertTrue(entidadesServicio.get(0).getPkId() == 100
                || entidadesServicio.get(0).getPkId() == 200);
    }

    @Test
    public void testObtenerPermisoPorPK() throws Exception {

        PermisoEntidad permisoEntidad = new PermisoEntidad();
        permisoEntidad.setPkId(100);

        when(permisosRepositorio.findById(100)).thenReturn(Optional.of(permisoEntidad));

        PermisoEntidad entidadServicio = permisosServicio.getPermisoPorPK(100);

        assertNotNull(entidadServicio);
        assertTrue(entidadServicio.getPkId() == 100);
    }
}
