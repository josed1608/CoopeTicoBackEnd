package com.coopetico.coopeticobackend.controladores.unit;

import com.coopetico.coopeticobackend.controladores.PermisosGrupoControlador;
import com.coopetico.coopeticobackend.entidades.GrupoEntidad;
import com.coopetico.coopeticobackend.entidades.PermisoEntidad;
import com.coopetico.coopeticobackend.entidades.PermisosGrupoEntidad;
import com.coopetico.coopeticobackend.entidades.PermisosGrupoEntidadPK;
import com.coopetico.coopeticobackend.servicios.GrupoServicio;
import com.coopetico.coopeticobackend.servicios.PermisoGrupoServicio;
import com.coopetico.coopeticobackend.servicios.PermisosServicio;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PermisoGrupoControladorUnitTest {

    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    PermisosGrupoControlador permisosGrupoControlador;

    @MockBean
    PermisoGrupoServicio permisoGrupoServicio;

    @MockBean
    GrupoServicio grupoServicio;

    @MockBean
    PermisosServicio permisosServicio;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(this.permisosGrupoControlador).build();
    }

    //ObjectMapper objectMapper = new ObjectMapper();
    //return objectMapper.writeValueAsString(obj);

    @Test
    public void testObtenerPermisosGrupo() throws Exception {
        String url = "/permisosGrupo";

        GrupoEntidad grupoEntidad = new GrupoEntidad("Cliente", null,null);
        PermisoEntidad permisoEntidad = new PermisoEntidad(100, "Pedir viaje", null);
        PermisoEntidad permisoEntidad2 = new PermisoEntidad(101, "Ver taxis cerca", null);

        List<PermisoEntidad> entidades = Arrays.asList(permisoEntidad, permisoEntidad2);
        given(grupoServicio.getGrupoPorPK("Cliente")).willReturn(grupoEntidad);
        given(permisoGrupoServicio.getPermisosGrupo(grupoEntidad)).willReturn(entidades);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url+"/Cliente").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        PermisoEntidad[] listaPermisos = objectMapper.readValue(content, PermisoEntidad[].class);
        assertTrue(listaPermisos.length == 2);
    }

    @Test
    public void testObtenerNoPermisosGrupo() throws Exception {
        String url = "/permisosGrupo/";

        GrupoEntidad grupoEntidad = new GrupoEntidad("Cliente", null,null);
        given(grupoServicio.getGrupoPorPK("Cliente")).willReturn(grupoEntidad);

        PermisoEntidad permisoEntidad = new PermisoEntidad(300, "Agregar Taxi", null);
        PermisoEntidad permisoEntidad2 = new PermisoEntidad(301, "Eliminar Taxi", null);
        PermisoEntidad permisoEntidad3 = new PermisoEntidad(302, "Editar Taxi", null);

        List<PermisoEntidad> permisosGenerales = new ArrayList<>();
        permisosGenerales.add(permisoEntidad);
        permisosGenerales.add(permisoEntidad2);
        permisosGenerales.add(permisoEntidad3);
        given(permisosServicio.getPermisos()).willReturn(permisosGenerales);

        List<PermisoEntidad> permisosGrupo = new ArrayList<>();
        permisosGrupo.add(permisoEntidad);
        permisosGrupo.add(permisoEntidad2);
        given(permisoGrupoServicio.getPermisosGrupo(grupoEntidad)).willReturn(permisosGrupo);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url+"-Cliente").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        PermisoEntidad[] listaPermisos = objectMapper.readValue(content, PermisoEntidad[].class);
        assertTrue(listaPermisos.length == 1);
        assertTrue(listaPermisos[0].getPkId() == permisoEntidad3.getPkId());
    }

    @Test
    public void testGuardarPermisosGrupo() throws Exception {
        String url = "/permisosGrupo/";

        PermisoEntidad permisoEntidad = new PermisoEntidad(300, "Agregar Taxi", null);
        given(permisosServicio.getPermisoPorPK(100)).willReturn(permisoEntidad);

        GrupoEntidad grupoEntidad = new GrupoEntidad("Cliente", null,null);
        given(grupoServicio.getGrupoPorPK("Cliente")).willReturn(grupoEntidad);

        PermisosGrupoEntidadPK permisosGrupoEntidadPK = new PermisosGrupoEntidadPK(100, "Cliente");

        List<PermisosGrupoEntidadPK> llaves = Arrays.asList(permisosGrupoEntidadPK);

        String objetos;

        ObjectMapper objectMapper = new ObjectMapper();
        objetos =  objectMapper.writeValueAsString(llaves);


        ResultActions mvcResult = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objetos))
                .andExpect(status().isOk());

    }

    @Test
    public void testEliminarPermisosGrupo() throws Exception {
        String url = "/permisosGrupo";

        PermisoEntidad permisoEntidad = new PermisoEntidad(300, "Agregar Taxi", null);
        GrupoEntidad grupoEntidad = new GrupoEntidad("Cliente", null,null);
        PermisosGrupoEntidadPK permisosGrupoEntidadPK = new PermisosGrupoEntidadPK(100, "Cliente");

        PermisosGrupoEntidad permisoGrupoEntidad = new PermisosGrupoEntidad(permisosGrupoEntidadPK, permisoEntidad, grupoEntidad);

        given(permisoGrupoServicio.getPermisoGrupoPorPK(permisosGrupoEntidadPK)).willReturn(permisoGrupoEntidad);

        ResultActions mvcResult = mockMvc.perform(delete(url+"/100/Cliente")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

    }

}
