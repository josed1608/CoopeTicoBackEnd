package com.coopetico.coopeticobackend.controladores.integration;

/*
 Pruebas de integración del ViajesControlador
 @author      Hannia Aguilar Salas
 @since       19-05-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.controladores.ViajeControlador;
import com.coopetico.coopeticobackend.entidades.UsuarioTemporal;
import com.coopetico.coopeticobackend.entidades.ViajeEntidadTemporal;
import com.coopetico.coopeticobackend.entidades.bd.*;
import com.coopetico.coopeticobackend.servicios.UsuarioServicio;
import com.coopetico.coopeticobackend.servicios.ViajesServicio;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ViajesControladorIntegrationTest {

    @Autowired
    protected WebApplicationContext wac;

    // Mock del mvc
    private MockMvc mockMvc;

    // Beans de las inyecciones de dependencias
    @Autowired
    ViajeControlador viajesControlador;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(viajesControlador).build();
    }

    /**
     * Test de obtener viajes
     */
    @Test
    public void testobtenerViajes() throws Exception {
        List<ViajeEntidadTemporal> viajesRetorno = viajesControlador.obtenerViajes();
        assertNotNull(viajesRetorno);
        assertEquals(viajesRetorno.size(), 3);
    }

    /**
     * Metodo para obtener un usuario para las pruebas
     * @return Retorna un objeto de tipo usuarioEntidad
     */
    public static UsuarioTemporal getUsuarioTemporal(){
        UsuarioTemporal usuarioTemporal = new UsuarioTemporal();
        usuarioTemporal.setCorreo("gerente11@gerente.com");
        usuarioTemporal.setNombre("Gerente");
        usuarioTemporal.setApellido1("Apellido1");
        usuarioTemporal.setApellido2("Apellido2");
        usuarioTemporal.setTelefono("11111111");
        usuarioTemporal.setContrasena("$2a$10$gJ0hUnsEvTp5zyBVo19IHe.GoYKkL3Wy268wGJxG5.k.tUFhSUify");
        usuarioTemporal.setFoto("foto");
        usuarioTemporal.setIdGrupo("Cliente");
        return usuarioTemporal;
    }

    /**
     * Metodo para obtener un usuario para las pruebas
     * @return Retorna un objeto de tipo usuarioTemporal
     */
    public static UsuarioEntidad getUsuarioEntidad(){
        return getUsuarioTemporal().convertirAUsuarioEntidad();
    }

}

