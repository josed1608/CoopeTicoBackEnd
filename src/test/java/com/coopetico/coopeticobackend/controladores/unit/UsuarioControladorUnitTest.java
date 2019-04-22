package com.coopetico.coopeticobackend.controladores.unit;

import com.coopetico.coopeticobackend.controladores.UsuarioControlador;
import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.servicios.ClienteServicio;
import com.coopetico.coopeticobackend.servicios.UsuarioServicio;
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
                .content("{"+
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
                        "}"
                ))
                //Assert
                .andExpect(status().isOk());
    }
}