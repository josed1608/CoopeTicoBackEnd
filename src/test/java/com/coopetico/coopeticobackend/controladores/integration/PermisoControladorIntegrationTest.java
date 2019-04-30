package com.coopetico.coopeticobackend.controladores.integration;

/**
 Test de integracion del PermisoControlador
 @author      Jefferson Alvarez
 @since       18-04-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.controladores.PermisoControlador;
import com.coopetico.coopeticobackend.entidades.PermisoEntidad;
import com.coopetico.coopeticobackend.repositorios.PermisosRepositorio;
import com.coopetico.coopeticobackend.servicios.PermisosServicio;
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
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class PermisoControladorIntegrationTest {
    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    PermisoControlador permisoControlador;

    @Autowired
    PermisosServicio permisosServicio;

    @Autowired
    PermisosRepositorio permisosRepositorio;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(this.permisoControlador).build();
        permisosRepositorio.deleteAll();
    }

    @Test
    @Transactional
    public void testGetPermisos() throws Exception {
        //Para que el test funcione la tabla de permisos debe estar vacia

        String url = "/permisos";

        PermisoEntidad permisoEntidad = new PermisoEntidad();
        permisoEntidad.setPkId(100);
        permisoEntidad.setDescripcion("Pedir Viaje");

        permisosRepositorio.save(permisoEntidad);

        PermisoEntidad permisoEntidad2 = new PermisoEntidad();
        permisoEntidad2.setPkId(101);
        permisoEntidad2.setDescripcion("Cancelar Viaje");

        permisosRepositorio.save(permisoEntidad2);


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String contenido = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        PermisoEntidad[] listaPermisos = objectMapper.readValue(contenido, PermisoEntidad[].class);
        assertTrue(listaPermisos.length > 0);
        //assertTrue(listaPermisos[0].getPkId() == 100);
    }
}
