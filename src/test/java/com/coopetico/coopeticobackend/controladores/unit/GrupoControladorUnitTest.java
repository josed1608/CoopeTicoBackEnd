package com.coopetico.coopeticobackend.controladores.unit;


import com.coopetico.coopeticobackend.controladores.GrupoControlador;
import com.coopetico.coopeticobackend.entidades.GrupoEntidad;
import com.coopetico.coopeticobackend.servicios.GrupoServicio;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
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
@RunWith(SpringRunner.class)
public class GrupoControladorUnitTest {

    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    GrupoControlador grupoControlador;

    @MockBean
    GrupoServicio grupoServicio;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(this.grupoControlador).build();
    }

    //ObjectMapper objectMapper = new ObjectMapper();
    //return objectMapper.writeValueAsString(obj);

    @Test
    public void testObtenerGrupos() throws Exception {
        String url = "/grupos";

        GrupoEntidad grupoEntidad = new GrupoEntidad();
        grupoEntidad.setPkId("Administrativo");

        GrupoEntidad grupoEntidad2 = new GrupoEntidad();
        grupoEntidad.setPkId("Cliente");

        List<GrupoEntidad> enitdades = Arrays.asList(grupoEntidad, grupoEntidad2);
        given(grupoServicio.getGrupos()).willReturn(enitdades);


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        GrupoEntidad[] listaGrupos = objectMapper.readValue(content, GrupoEntidad[].class);
        assertTrue(listaGrupos.length > 0);
    }

}
