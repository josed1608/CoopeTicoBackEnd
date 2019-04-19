package com.coopetico.coopeticobackend.servicios.unit;

/**
 Test de unidad del GrupoServicio
 @author      Jefferson Alvarez
 @since       18-04-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.entidades.GrupoEntidad;
import com.coopetico.coopeticobackend.repositorios.GruposRepositorio;
import com.coopetico.coopeticobackend.servicios.GrupoServicio;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GrupoServicioUnitTest {

    @Autowired
    protected WebApplicationContext wac;

    @MockBean
    GrupoServicio grupoServicio;

    @MockBean
    GruposRepositorio gruposRepositorio;


    @Test
    public void testObtenerGrupos() throws Exception {

        GrupoEntidad grupoEntidad = new GrupoEntidad();
        grupoEntidad.setPkId("Administrativo");

        GrupoEntidad grupoEntidad2 = new GrupoEntidad();
        grupoEntidad2.setPkId("Cliente");

        List<GrupoEntidad> entidades = Arrays.asList(grupoEntidad, grupoEntidad2);
        when(gruposRepositorio.getIDGrupos()).thenReturn(entidades);


        List<GrupoEntidad> enitdadesServicio = grupoServicio.getGrupos();

        assertNotNull(enitdadesServicio);
    }

    @Test
    public void testObtenerGrupoPorPK() throws Exception {

        GrupoEntidad grupoEntidad = new GrupoEntidad();
        grupoEntidad.setPkId("Administrativo");

        when(gruposRepositorio.findById("Administrativo")).thenReturn(Optional.of(grupoEntidad));

        Optional<GrupoEntidad> entidadServicio = gruposRepositorio.findById("Administrativo");

        assertThat(entidadServicio).isNotEmpty();
        assertThat(entidadServicio).hasValue(grupoEntidad);
    }
}
