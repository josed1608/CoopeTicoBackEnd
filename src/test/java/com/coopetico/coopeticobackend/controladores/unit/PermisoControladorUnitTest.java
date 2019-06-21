package com.coopetico.coopeticobackend.controladores.unit;

/**
 Test de unidad del PermisoControlador
 @author      Jefferson Alvarez
 @since       18-04-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.Utilidades.MockMvcUtilidades;
import com.coopetico.coopeticobackend.Utilidades.TokenUtilidades;
import com.coopetico.coopeticobackend.controladores.PermisoControlador;
import com.coopetico.coopeticobackend.entidades.bd.PermisoEntidad;
import com.coopetico.coopeticobackend.servicios.PermisosServicio;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class PermisoControladorUnitTest {

    private MockMvc mockMvc;

    @Autowired
    TokenUtilidades tokenUtilidades;

    @Autowired
    PermisoControlador permisoControlador;

    @MockBean
    PermisosServicio permisosServicio;

    @Before
    public void setup() {
        this.mockMvc = MockMvcUtilidades.getMockMvc();
    }

    //ObjectMapper objectMapper = new ObjectMapper();
    //return objectMapper.writeValueAsString(obj);

    @Test
    public void testObtenerPermisos() throws Exception {
        String url = "/permisos";

        PermisoEntidad permisoEntidad = new PermisoEntidad();
        permisoEntidad.setPkId(100);
        permisoEntidad.setDescripcion("Pedir Viaje");

        PermisoEntidad permisoEntidad2 = new PermisoEntidad();
        permisoEntidad2.setPkId(101);
        permisoEntidad2.setDescripcion("Cancelar Viaje");

        List<PermisoEntidad> entidades = Arrays.asList(permisoEntidad, permisoEntidad2);
        given(permisosServicio.getPermisos()).willReturn(entidades);


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url).headers(tokenUtilidades.obtenerTokenGerente()).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String contenido = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        PermisoEntidad[] listaPermisos = objectMapper.readValue(contenido, PermisoEntidad[].class);
        assertTrue(listaPermisos.length == 2);
    }

}
