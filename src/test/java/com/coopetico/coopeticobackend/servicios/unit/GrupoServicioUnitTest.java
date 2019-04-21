package com.coopetico.coopeticobackend.servicios.unit;

/**
 Test de unidad del GrupoServicio
 @author      Jefferson Alvarez
 @since       18-04-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.entidades.GrupoEntidad;
import com.coopetico.coopeticobackend.repositorios.GruposRepositorio;
import com.coopetico.coopeticobackend.servicios.GrupoServicioImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class GrupoServicioUnitTest {

    @Autowired
    protected WebApplicationContext wac;

    @MockBean
    GruposRepositorio gruposRepositorio;

    @InjectMocks
    GrupoServicioImpl grupoServicio;

    @Before
    public void setUp() {
        gruposRepositorio = mock(GruposRepositorio.class);
        grupoServicio = new GrupoServicioImpl(gruposRepositorio);
        MockitoAnnotations.initMocks( this );
    }


    @Test
    public void testObtenerGrupos() throws Exception {

        GrupoEntidad grupoEntidad = new GrupoEntidad();
        grupoEntidad.setPkId("Administrativo");

        GrupoEntidad grupoEntidad2 = new GrupoEntidad();
        grupoEntidad2.setPkId("Cliente");

        List<GrupoEntidad> entidades = Arrays.asList(grupoEntidad, grupoEntidad2);
        when(gruposRepositorio.getIDGrupos()).thenReturn(entidades);


        List<GrupoEntidad> entidadesServicio = grupoServicio.getGrupos();

        assertNotNull(entidadesServicio);
        assertTrue(entidadesServicio.size() == 2);
        assertTrue(entidadesServicio.get(0).getPkId() == "Administrativo"
        || entidadesServicio.get(0).getPkId() == "Cliente");
    }

    @Test
    public void testObtenerGrupoPorPK() throws Exception {

        GrupoEntidad grupoEntidad = new GrupoEntidad();
        grupoEntidad.setPkId("Administrativo");

        when(gruposRepositorio.findById("Administrativo")).thenReturn(Optional.of(grupoEntidad));

        GrupoEntidad entidadServicio = grupoServicio.getGrupoPorPK("Administrativo");

        assertNotNull(entidadServicio);
        assertTrue(entidadServicio.getPkId() == "Administrativo");
    }
}
