package com.coopetico.coopeticobackend.controladores.integration;

/**
 Test de unidad del PermisoGrupoControlador
 @author      Jefferson Alvarez
 @since       18-04-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.controladores.PermisosGrupoControlador;
import com.coopetico.coopeticobackend.entidades.GrupoEntidad;
import com.coopetico.coopeticobackend.entidades.PermisoEntidad;
import com.coopetico.coopeticobackend.entidades.PermisosGrupoEntidad;
import com.coopetico.coopeticobackend.entidades.PermisosGrupoEntidadPK;
import com.coopetico.coopeticobackend.repositorios.GruposRepositorio;
import com.coopetico.coopeticobackend.repositorios.PermisosGruposRepositorio;
import com.coopetico.coopeticobackend.repositorios.PermisosRepositorio;
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

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PermisoGrupoControladorIntegrationTest {

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

    @MockBean
    PermisosGruposRepositorio permisosGruposRepositorio;

    @MockBean
    GruposRepositorio gruposRepositorios;

    @MockBean
    PermisosRepositorio permisosRepositorio;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(this.permisosGrupoControlador).build();
    }

    @Test
    @Transactional
    public void testObtenerPermisosGrupo() throws Exception {
        String url = "/permisosGrupo";

        GrupoEntidad grupoEntidad = new GrupoEntidad("Cliente", null,null);
        PermisoEntidad permisoEntidad = new PermisoEntidad(100, "Pedir viaje", null);
        PermisosGrupoEntidadPK permisosGrupoEntidadPK = new PermisosGrupoEntidadPK(100, "Cliente");

        PermisosGrupoEntidad permisosGrupoEntidad = new PermisosGrupoEntidad(permisosGrupoEntidadPK, permisoEntidad, grupoEntidad);

        permisosRepositorio.save(permisoEntidad);
        gruposRepositorios.save(grupoEntidad);

        permisosGruposRepositorio.save(permisosGrupoEntidad);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url+"/Cliente").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

    }

    @Test
    @Transactional
    public void testObtenerNoPermisosGrupo() throws Exception {
        String url = "/permisosGrupo/";

        GrupoEntidad grupoEntidad = new GrupoEntidad("Cliente", null,null);
        gruposRepositorios.save(grupoEntidad);

        PermisoEntidad permisoEntidad = new PermisoEntidad(300, "Agregar Taxi", null);
        PermisoEntidad permisoEntidad2 = new PermisoEntidad(301, "Eliminar Taxi", null);
        PermisoEntidad permisoEntidad3 = new PermisoEntidad(302, "Editar Taxi", null);

        permisosRepositorio.save(permisoEntidad);
        permisosRepositorio.save(permisoEntidad2);
        permisosRepositorio.save(permisoEntidad3);

        PermisosGrupoEntidadPK permisosGrupoEntidadPK = new PermisosGrupoEntidadPK(300, "Cliente");

        PermisosGrupoEntidad permisosGrupoEntidad = new PermisosGrupoEntidad(permisosGrupoEntidadPK, permisoEntidad, grupoEntidad);
        permisosGruposRepositorio.save(permisosGrupoEntidad);


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url+"-Cliente").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

    }

    @Test
    @Transactional
    public void testGuardarPermisosGrupo() throws Exception {
        String url = "/permisosGrupo/";

        PermisoEntidad permisoEntidad = new PermisoEntidad(100, "Agregar Taxi", null);
        permisosRepositorio.save(permisoEntidad);

        GrupoEntidad grupoEntidad = new GrupoEntidad("Cliente", null,null);
        gruposRepositorios.save(grupoEntidad);

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
    @Transactional


    public void testEliminarPermisosGrupo() throws Exception {
        String url = "/permisosGrupo";

        PermisoEntidad permisoEntidad = new PermisoEntidad(300, "Agregar Taxi", null);
        GrupoEntidad grupoEntidad = new GrupoEntidad("Cliente", null,null);
        PermisosGrupoEntidadPK permisosGrupoEntidadPK = new PermisosGrupoEntidadPK(100, "Cliente");

        PermisosGrupoEntidad permisoGrupoEntidad = new PermisosGrupoEntidad(permisosGrupoEntidadPK, permisoEntidad, grupoEntidad);

        permisosGruposRepositorio.save(permisoGrupoEntidad);

        ResultActions mvcResult = mockMvc.perform(delete(url+"/100/Cliente/")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

    }

}
