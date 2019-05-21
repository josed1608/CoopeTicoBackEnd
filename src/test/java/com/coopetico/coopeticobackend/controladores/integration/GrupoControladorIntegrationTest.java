package com.coopetico.coopeticobackend.controladores.integration;

/**
 Test de integracion del GrupoControlador
 @author      Jefferson Alvarez
 @since       18-04-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.controladores.GrupoControlador;
import com.coopetico.coopeticobackend.entidades.bd.GrupoEntidad;
import com.coopetico.coopeticobackend.repositorios.GruposRepositorio;
import com.coopetico.coopeticobackend.servicios.GrupoServicio;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class GrupoControladorIntegrationTest {
    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    GrupoControlador grupoControlador;

    @Autowired
    GrupoServicio grupoServicio;

    @Autowired
    GruposRepositorio gruposRepositorio;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(this.grupoControlador).build();
    }

    @Test
    @Transactional
    public void testGetGrupos() throws Exception {

        String url = "/grupos";

        GrupoEntidad grupoEntidad = new GrupoEntidad();
        grupoEntidad.setPkId("Administrativo");

        gruposRepositorio.save(grupoEntidad);

        GrupoEntidad grupoEntidad2 = new GrupoEntidad();
        grupoEntidad2.setPkId("Cliente");

        gruposRepositorio.save(grupoEntidad2);

        GrupoEntidad grupoEntidad3 = new GrupoEntidad();
        grupoEntidad3.setPkId("Taxista");

        gruposRepositorio.save(grupoEntidad3);


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String contenido = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        GrupoEntidad[] listaGrupos = objectMapper.readValue(contenido, GrupoEntidad[].class);
        assertNotNull(listaGrupos);
        assertTrue(listaGrupos.length > 0);
    }
}
