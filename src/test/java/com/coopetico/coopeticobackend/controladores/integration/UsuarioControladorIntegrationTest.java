package com.coopetico.coopeticobackend.controladores.integration;

/**
 Pruebas de integración del UsuarioControlador
 @author      Hannia Aguilar Salas
 @since       22-04-2019
 @version:    1.0
 */


import com.coopetico.coopeticobackend.controladores.TaxistasControlador;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.coopetico.coopeticobackend.controladores.ClienteControlador;
import com.coopetico.coopeticobackend.controladores.UsuarioControlador;
import com.coopetico.coopeticobackend.excepciones.UsuarioNoEncontradoExcepcion;
import com.coopetico.coopeticobackend.servicios.ClienteServicio;
import com.coopetico.coopeticobackend.servicios.UsuarioServicio;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UsuarioControladorIntegrationTest {
    /**
     * Mock para llamar el controlador.
     */
    private MockMvc mockMvc;

    /**
     * Controlador del taxista.
     */
    @Autowired
    UsuarioControlador usuarioControlador;

    /**
     * Inicializador del mock para hacer las consultas.
     */
    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(this.usuarioControlador).build();
    }

    @Test
    public void testActivarCuenta()throws Exception {
        final String resultado = mockMvc.perform(put("/usuarios/gerente@gerente.com/estado?valido=true"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(resultado);
        assertEquals(resultado, "{\"mensaje\" : \"Se ha habilitado al usuario.\"}");
    }

    @Test
    public void testDesactivarCuenta()throws Exception {
        final String resultado = mockMvc.perform(put("/usuarios/gerente@gerente.com/estado?valido=false"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(resultado);
        assertEquals(resultado, "{\"mensaje\" : \"Se ha deshabilitado al usuario.\"}");
    }

    @Test
    public void testDesactivarCuentaUsuarioNoExistente()throws Exception {
        final String resultado = mockMvc.perform(put("/usuarios/noExiste@gerente.com/estado?valido=false"))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        System.out.println(resultado);
        assertEquals(resultado, "{\"error\": \"El usuario no existe.\"}");
    }
}
