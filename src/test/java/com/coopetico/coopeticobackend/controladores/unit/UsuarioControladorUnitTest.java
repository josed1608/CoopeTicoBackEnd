package com.coopetico.coopeticobackend.controladores.unit;

import com.coopetico.coopeticobackend.controladores.UsuarioControlador;
import com.coopetico.coopeticobackend.entidades.GrupoEntidad;
import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.entidades.UsuarioTemporal;
import com.coopetico.coopeticobackend.servicios.ClienteServicio;
import com.coopetico.coopeticobackend.servicios.UsuarioServicio;
/**
 Pruebas de unidad del UsuarioControlador
 @author      Hannia Aguilar Salas
 @since       21-04-2019
 @version:    1.0
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UsuarioControladorUnitTest {
    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;
    @Autowired
    UsuarioControlador usuarioControlador;

    @MockBean
    UsuarioServicio usuarioServicio;


    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(this.usuarioControlador).build();
    }

    @Test
    public void testCrearUsuario() throws Exception {
        doAnswer((i)->{return null;}).when(usuarioServicio).agregarUsuario(any(UsuarioEntidad.class), anyString());

        //Act
        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getUsuario()))
                //Assert
                .andExpect(status().isOk());
    }

    @Test
    public void testObtenerUsuarios() throws Exception {
        String url = "/usuarios";

        List<UsuarioEntidad> usuarios = new ArrayList<>();
        for (int i = 0; i < 10; ++i){
            usuarios.add(getUsuarioTemporal().convertirAUsuarioEntidad());
        }
        given(usuarioServicio.obtenerUsuarios()).willReturn(usuarios);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        UsuarioTemporal[] listaGrupos = objectMapper.readValue(content, UsuarioTemporal[].class);
        assertTrue(listaGrupos.length == 10);
    }




    public static String getUsuario(){
        return "{"+
                "\"pkCorreo\": \"gerente11@gerente.com\","+
                "\"nombre\": \"Gerente\","+
                "\"apellidos\": \"apellido\","+
                "\"telefono\": \"11111111\","+
                "\"contrasena\": \"$2a$10$gJ0hUnsEvTp5zyBVo19IHe.GoYKkL3Wy268wGJxG5.k.tUFhSUify\","+
                "\"foto\": \"foto\","+
                "\"clienteByPkCorreo\": null,"+
                "\"coopeticoByPkCorreo\": null,"+
                "\"taxistaByPkCorreo\": null,"+
                "\"grupoByIdGrupo\": {"+
                "\"pkId\": \"Gerente\""+
                "}";
    }

    public static UsuarioTemporal getUsuarioTemporal(){
        UsuarioTemporal usuarioTemporal = new UsuarioTemporal();
        usuarioTemporal.setCorreo("gerente11@gerente.com");
        usuarioTemporal.setNombre("Nombre");
        usuarioTemporal.setApellido1("Apellido1");
        usuarioTemporal.setApellido2("Apellido2");
        usuarioTemporal.setCorreo("a@ad.com");
        usuarioTemporal.setTelefono("88884444");
        usuarioTemporal.setIdGrupo("Cliente");
        return usuarioTemporal;
    }

    public String convertirAJson(UsuarioEntidad usuarioEntidad){
        Gson gson = new Gson();
        return gson.toJson(usuarioEntidad);
    }
}

