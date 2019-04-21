package com.coopetico.coopeticobackend.servicios.integration;
/**
 Pruebas de integracion para el servicio de taxistas.
 @author      Christofer Rodriguez
 @since       20-04-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.entidades.TaxistaEntidadTemporal;
import com.coopetico.coopeticobackend.repositorios.TaxistasRepositorio;
import com.coopetico.coopeticobackend.servicios.TaxistasServicioImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.transaction.Transactional;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Pruebas de integracion para el servicio de taxistas.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TaxistasServicioImplIntegrationTest {

    /**
     * Repositorio de taxistas.
     */
    @Autowired
    TaxistasRepositorio taxistasRepositorio;

    /**
     * Servicio de taxistas.
     */
    @Autowired
    TaxistasServicioImpl taxistasServicio;

    /**
     * Prueba de integracion para consultar todos los taxistas.
     */
    @Test
    @Transactional
    public void testConsultar() throws Exception {
        // Se piden los taxistas al servicio
        List<TaxistaEntidadTemporal> entidadesServicio = taxistasServicio.consultar();
        //Se compara que no sea nulo
        assertNotNull(entidadesServicio);
        //Se comprueba que contengan 1 taxistas
        assertEquals(entidadesServicio.size(), 1);
    }

    /**
     * Prueba de integracion para consultar un taxista en especifico.
     */
    @Test
    @Transactional
    public void testConsultarPorId() throws Exception {
        // Se le pide el taxista al servicio
        TaxistaEntidadTemporal entidadRetornada = taxistasServicio.consultarPorId("taxista@taxista.com");
        //Se compara que no sea nulo
        assertNotNull(entidadRetornada);
        //Se compara que sea el taxista solicitado
        assertTrue(entidadRetornada.getPkCorreoUsuario().equals("taxista@taxista.com"));
    }
}