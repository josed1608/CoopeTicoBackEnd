package com.coopetico.coopeticobackend.servicios.unit;


import com.coopetico.coopeticobackend.entidades.GrupoEntidad;
import com.coopetico.coopeticobackend.repositorios.GruposRepositorio;
import com.coopetico.coopeticobackend.servicios.GrupoServicio;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GrupoServicioUnitTest {
    private MockMvc mockMvc;

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
        grupoEntidad.setPkId("Cliente");

        when(gruposRepositorio.getIDGrupos()).thenReturn(new LinkedList<>());


        List<GrupoEntidad> enitdadesServicio = grupoServicio.getGrupos();

        assertTrue(enitdadesServicio != null);
    }

}
