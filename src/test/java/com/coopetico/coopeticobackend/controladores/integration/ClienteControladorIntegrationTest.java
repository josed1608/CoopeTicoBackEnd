package com.coopetico.coopeticobackend.controladores.integration;

import com.coopetico.coopeticobackend.controladores.AuthControlador;
import com.coopetico.coopeticobackend.controladores.ClienteControlador;
import com.coopetico.coopeticobackend.excepciones.UsuarioNoEncontradoExcepcion;
import com.coopetico.coopeticobackend.servicios.ClienteServicio;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ClienteControladorIntegrationTest {
    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    ClienteControlador clienteControlador;
    @Autowired
    ClienteServicio clienteServicio;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(this.clienteControlador).build();
    }

    @Test
    @Transactional
    public void testCrearUsuarioSuccesfull() throws Exception {
        try {
            clienteServicio.borrarCliente("prueba@prueba.com");
        }
        catch (UsuarioNoEncontradoExcepcion ignored) {}
        finally {
            mockMvc.perform(post("/clientes")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{" +
                            "\"pkCorreo\": \"prueba@prueba.com\"," +
                            "\"nombre\": \"Eugenio\"," +
                    "\"apellidos\": \"Morera Soto\"," +
                    "\"telefono\": \"75842654\"," +
                    "\"contrasena\": \"contrasenna\"" +
                            "}"))
                    .andExpect(status().isCreated());
        }
    }
}
