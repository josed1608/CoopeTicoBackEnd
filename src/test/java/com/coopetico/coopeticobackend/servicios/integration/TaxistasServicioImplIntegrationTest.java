package com.coopetico.coopeticobackend.servicios.integration;
/**
 Pruebas de integracion para el servicio de taxistas.
 @author      Christofer Rodriguez
 @since       20-04-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.entidades.TaxistaEntidadTemporal;
import com.coopetico.coopeticobackend.excepciones.UsuarioNoEncontradoExcepcion;
import com.coopetico.coopeticobackend.repositorios.TaxistasRepositorio;
import com.coopetico.coopeticobackend.servicios.TaxistasServicioImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

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
        assertEquals(entidadesServicio.size(), 4);
    }

    /**
     * Prueba de integracion para consultar un taxista en especifico.
     */
    @Test
    @Transactional
    public void testConsultarPorId() throws Exception {
        // Se le pide el taxista al servicio
        TaxistaEntidadTemporal entidadRetornada = taxistasServicio.consultarPorId("taxista1@taxista.com");
        //Se compara que no sea nulo
        assertNotNull(entidadRetornada);
        //Se compara que sea el taxista solicitado
        assertEquals(entidadRetornada.getPkCorreoUsuario(), "taxista1@taxista.com");
    }

    /**
     * Prueba de integracion para consultar la fecha de vencimiento de licencia de un taxista en el servicio.
     */
    @Test
    @Transactional
    public void testConsultarVencLic() throws Exception {
        // Se le pide el taxista al servicio
        TaxistaEntidadTemporal entidadRetornada = taxistasServicio.consultarPorId("taxista1@taxista.com");
        //Se compara que no sea nulo
        assertNotNull(entidadRetornada);
        //Se compara que la fecha sea la esperada
        long respCorrecta = (long)1556679600 * 1000;
        long fecha = entidadRetornada.getVence_licencia().getTime();
        Assert.assertEquals( fecha, respCorrecta);
    }

    /**
     * Verifica que se devuelva un mapa con los datos del estado
     * @author Kevin Jiménez
     */
    @Test
    public void testObtenerEstadoNoSuspendido() {
        // Solicita un mapa con la información sobre el estado
        Map<String, Object> estado = taxistasServicio.obtenerEstado("taxistaNoSuspendido@taxista.com");
        // Verifica que exista el campo justificación
        assertTrue(estado.containsKey("justificacion"));
        // Verifica que exista el campo estado
        assertTrue(estado.containsKey("estado"));
        // Verifica que el estado no se nulo
        assertTrue(estado.get("estado").equals(true));
    }


    /**
     * Verifica que se devuelva un mapa con los datos del estado
     * @author Kevin Jiménez
     */
    @Test
    public void testObtenerEstadoSuspendido() {
        // Solicita un mapa con la información sobre el estado
        Map<String, Object> estado = taxistasServicio.obtenerEstado("taxistaSuspendido@taxista.com");
        // Verifica que exista el campo justificación
        assertTrue(estado.containsKey("justificacion"));
        // Verifica que exista el campo estado
        assertTrue(estado.containsKey("estado"));
        // Verifica que el estado no se nulo
        assertTrue(estado.get("estado").equals(false));
        // Verifica que exista el campo estado
        assertTrue(estado.containsKey("justificacion"));
        // Verifica que el estado no se nulo
        assertTrue(estado.get("justificacion").equals("Cobro de más a un cliente"));
    }

    // Kevin Jiménez
    /**
     * Verifica que al proveer un correo que no existe, lance una excepcion
     */
    @Test(expected = UsuarioNoEncontradoExcepcion.class)
    public void testObtenerEstadoCorreoInvalidoExcepcion() {
        // Solicita un mapa con la información sobre el estado
        taxistasServicio.obtenerEstado("axia1@xista.com");
    }
}