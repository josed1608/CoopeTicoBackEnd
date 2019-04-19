package com.coopetico.coopeticobackend.servicios.unit;

/**
 Test de unidad del PermisoServicio
 @author      Jefferson Alvarez
 @since       18-04-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.entidades.PermisoEntidad;
import com.coopetico.coopeticobackend.repositorios.PermisosRepositorio;
import com.coopetico.coopeticobackend.servicios.PermisosServicio;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PermisoServicioUnitTest {

    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @MockBean
    PermisosServicio permisosServicio;

    @MockBean
    PermisosRepositorio permisosRepositorio;


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
    }

    @Test
    public void testObtenerPermisoPorPK() throws Exception {

        PermisoEntidad permisoEntidad = new PermisoEntidad();
        permisoEntidad.setPkId(100);

        when(permisosRepositorio.findById(100)).thenReturn(Optional.of(permisoEntidad));

        Optional<PermisoEntidad> entidadServicio = permisosRepositorio.findById(100);

        assertThat(entidadServicio).isNotEmpty();
        assertThat(entidadServicio).hasValue(permisoEntidad);
    }
}
