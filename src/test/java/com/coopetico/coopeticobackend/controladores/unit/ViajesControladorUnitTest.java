package com.coopetico.coopeticobackend.controladores.unit;

/*
 Pruebas de unidad del ViajesControlador
 @author      Hannia Aguilar Salas
 @since       19-05-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.controladores.ViajeControlador;
import com.coopetico.coopeticobackend.entidades.bd.ViajeEntidad;
import com.coopetico.coopeticobackend.servicios.ViajesServicio;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.model.LatLng;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ViajesControladorUnitTest {

    @Autowired
    protected WebApplicationContext wac;

    // Mock del mvc
    private MockMvc mockMvc;

    // Beans de las inyecciones de dependencias
    @Autowired
    ViajeControlador viajesControlador;

    // Mock
    @MockBean
    ViajesServicio viajeServicio;


    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(viajesControlador).build();
    }

    /**
     * Metodo que devuelve una lista con tres viajes para realizar las pruebas
     * @return Listas con viajes
     */
    public static List<ViajeEntidad> getListaViajesEntidad(){
        List<ViajeEntidad> viajes = new ArrayList<>();
        Date date = new Date();
        // viajes.add(new ViajeEntidad("AAA111", true, "11112222", "A", "Auto", new Timestamp(date.getTime()), new Timestamp(date.getTime()), new Timestamp(date.getTime()),null,"foto.jpg", true,"correo@correo.com",null));

        return viajes;
    }

}

