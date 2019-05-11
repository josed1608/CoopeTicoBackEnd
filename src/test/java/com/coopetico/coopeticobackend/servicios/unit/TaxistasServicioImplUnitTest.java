package com.coopetico.coopeticobackend.servicios.unit;

/**
 Pruebas de unidad para el servicio de taxistas.
 @author      Christofer Rodriguez
 @since       19-04-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.entidades.TaxistaEntidad;
import com.coopetico.coopeticobackend.entidades.TaxistaEntidadTemporal;
import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.repositorios.TaxisRepositorio;
import com.coopetico.coopeticobackend.repositorios.TaxistasRepositorio;
import com.coopetico.coopeticobackend.servicios.TaxistasServicioImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Pruebas de unidad para el servicio de taxistas.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TaxistasServicioImplUnitTest {

    /**
     * Contexto.
     */
    @Autowired
    protected WebApplicationContext wac;

    /**
     * Repositorio de taxistas.
     */
    @MockBean
    TaxistasRepositorio taxistasRepositorio;

    /**
     * Repositorio de taxis.
     */
    @MockBean
    TaxisRepositorio taxisRepositorio;

    /**
     * Servicio de taxistas.
     */
    @InjectMocks
    TaxistasServicioImpl taxistasServicio;

    /**
     * Inicializador del mock.
     */
    @Before
    public void setUp() {
        taxistasServicio = new TaxistasServicioImpl();
        MockitoAnnotations.initMocks( this );
    }

    /**
     * Prueba de unidad del consultar de todos los taxistas.
     */
    @Test
    public void testConsultar() throws Exception {
        //Primer taxista
        UsuarioEntidad usuario1 = new UsuarioEntidad();
        usuario1.setNombre("Taxista1");
        usuario1.setApellido1("Apellido1");
        usuario1.setApellido2("Apellido2");
        usuario1.setPkCorreo("taxistaMoka1@coopetico.com");
        usuario1.setTelefono("22333322");
        usuario1.setFoto("foto");
        TaxistaEntidad taxistaEntidad1 = new TaxistaEntidad();
        taxistaEntidad1.setPkCorreoUsuario("taxistaMoka1@coopetico.com");
        taxistaEntidad1.setFaltas("0");
        taxistaEntidad1.setEstado(true);
        taxistaEntidad1.setHojaDelincuencia(true);
        taxistaEntidad1.setEstrellas(5);
        taxistaEntidad1.setUsuarioByPkCorreoUsuario(usuario1);
        //Segundo taxista
        UsuarioEntidad usuario2 = new UsuarioEntidad();
        usuario2.setNombre("Taxista1");
        usuario2.setApellido1("Apellido1");
        usuario2.setApellido2("Apellido2");
        usuario2.setPkCorreo("taxistaMoka1@coopetico.com");
        usuario2.setTelefono("22333322");
        usuario2.setFoto("foto");
        TaxistaEntidad taxistaEntidad2 = new TaxistaEntidad();
        taxistaEntidad2.setPkCorreoUsuario("taxistaMoka1@coopetico.com");
        taxistaEntidad2.setFaltas("0");
        taxistaEntidad2.setEstado(true);
        taxistaEntidad2.setHojaDelincuencia(true);
        taxistaEntidad2.setEstrellas(5);
        taxistaEntidad2.setUsuarioByPkCorreoUsuario(usuario2);
        // Se le indica al mock que retorne esa lista cuando consultan al repo
        List<TaxistaEntidad> entidades = Arrays.asList(taxistaEntidad1, taxistaEntidad2);
        when(taxistasRepositorio.findAll()).thenReturn(entidades);
        when(taxisRepositorio.findAll()).thenReturn(null);
        // Se piden los taxistas al servicio
        List<TaxistaEntidadTemporal> entidadesServicio = taxistasServicio.consultar();
        //Se compara que no sea nulo
        assertNotNull(entidadesServicio);
        //Se comprueba que contengan 2 taxistas
        assertEquals(entidadesServicio.size(), 2);
    }

    /**
     * Prueba de unidad de consultar un taxista en especifico.
     */
    @Test
    public void testConsultarPorId() throws Exception {
        //Primer taxista
        UsuarioEntidad usuario = new UsuarioEntidad();
        usuario.setNombre("Taxista1");
        usuario.setApellido1("Apellido1");
        usuario.setApellido2("Apellido2");
        usuario.setPkCorreo("taxistaMoka1@coopetico.com");
        usuario.setTelefono("22333322");
        usuario.setFoto("foto");
        TaxistaEntidad taxistaEntidad1 = new TaxistaEntidad();
        taxistaEntidad1.setPkCorreoUsuario("taxistaMoka1@coopetico.com");
        taxistaEntidad1.setFaltas("0");
        taxistaEntidad1.setEstado(true);
        taxistaEntidad1.setHojaDelincuencia(true);
        taxistaEntidad1.setEstrellas(5);
        taxistaEntidad1.setUsuarioByPkCorreoUsuario(usuario);
        //Se le indica al mock que cuando pregunten al repo por ese taxista que retorne ese taxista.
        when(taxistasRepositorio.findById("taxistaMoka1@coopetico.com")).thenReturn(Optional.of(taxistaEntidad1));
        // Se le pide el taxista al servicio
        TaxistaEntidadTemporal entidadRetornada = taxistasServicio.consultarPorId("taxistaMoka1@coopetico.com");
        //Se compara que no sea nulo
        assertNotNull(entidadRetornada);
        //Se compara que sea el taxista solicitado
        assertEquals(entidadRetornada.getPkCorreoUsuario(), "taxistaMoka1@coopetico.com");
    }

    /**
     * Prueba de unidad para consultar la fecha de vencimiento de licencia de un taxista en el servicio.
     */
    @Test
    public void testConsultarVencLic() throws Exception {
        //Primer taxista
        UsuarioEntidad usuario = new UsuarioEntidad();
        usuario.setNombre("Taxista1");
        usuario.setApellido1("Apellido1");
        usuario.setApellido2("Apellido2");
        usuario.setPkCorreo("taxistaMoka1@coopetico.com");
        usuario.setTelefono("22333322");
        usuario.setFoto("foto");
        TaxistaEntidad taxistaEntidad1 = new TaxistaEntidad();
        taxistaEntidad1.setPkCorreoUsuario("taxistaMoka1@coopetico.com");
        taxistaEntidad1.setFaltas("0");
        taxistaEntidad1.setEstado(true);
        taxistaEntidad1.setHojaDelincuencia(true);
        taxistaEntidad1.setEstrellas(5);
        taxistaEntidad1.setUsuarioByPkCorreoUsuario(usuario);
        taxistaEntidad1.setVence_licencia(new Timestamp((long)1556679600 * 1000));
        //Se le indica al mock que cuando pregunten al repo por ese taxista que retorne ese taxista.
        when(taxistasRepositorio.findById("taxistaMoka1@coopetico.com")).thenReturn(Optional.of(taxistaEntidad1));
        // Se le pide el taxista al servicio
        TaxistaEntidadTemporal entidadRetornada = taxistasServicio.consultarPorId("taxistaMoka1@coopetico.com");
        //Se compara que no sea nulo
        assertNotNull(entidadRetornada);
        //Se compara que la fecha sea la esperada
        long respCorrecta = (long)1556679600 * 1000;
        long resp = entidadRetornada.getVence_licencia().getTime();
        assertEquals(resp, respCorrecta);
    }

    /**
     * Prueba que se devuelva el mapa de forma correcta con un usuario no supendido
     * @author Kevin Jiménez
     */
    @Test
    public void testObtenerEstadoTaxistaNoSuspendido(){
        //Primer taxista
        UsuarioEntidad usuario = new UsuarioEntidad();
        usuario.setNombre("Taxista1");
        usuario.setApellido1("Apellido1");
        usuario.setApellido2("Apellido2");
        usuario.setPkCorreo("taxistaNoSuspendido@taxista.com");
        usuario.setTelefono("22333322");
        usuario.setFoto("foto");

        TaxistaEntidad taxista = new TaxistaEntidad();
        taxista.setPkCorreoUsuario("taxistaNoSuspendido@taxista.com");
        taxista.setFaltas("0");
        taxista.setEstado(true);
        taxista.setHojaDelincuencia(true);
        taxista.setEstrellas(5);
        taxista.setJustificacion("");
        taxista.setUsuarioByPkCorreoUsuario(usuario);

        when(taxistasRepositorio.existsById("taxistaNoSuspendido@taxista.com")).thenReturn(true);
        when(taxistasRepositorio.findById("taxistaNoSuspendido@taxista.com")).thenReturn(Optional.of(taxista));

        Map<String, Object> estado = taxistasServicio.obtenerEstado("taxistaNoSuspendido@taxista.com");

        assertTrue(estado.get("estado").equals(true));
        assertTrue(estado.get("justificacion").equals(""));
    }

    /**
     * Prueba que se devuelva el mapa de forma correcta con un usuario supendido
     * @author Kevin Jiménez
     */
    @Test
    public void testObtenerEstadoTaxistaSuspendido(){
        //Primer taxista
        UsuarioEntidad usuario = new UsuarioEntidad();
        usuario.setNombre("Taxista1");
        usuario.setApellido1("Apellido1");
        usuario.setApellido2("Apellido2");
        usuario.setPkCorreo("taxistaSuspendido@taxista.com");
        usuario.setTelefono("22333322");
        usuario.setFoto("foto");

        TaxistaEntidad taxista = new TaxistaEntidad();
        taxista.setPkCorreoUsuario("taxistaSuspendido@taxista.com");
        taxista.setFaltas("0");
        taxista.setEstado(false);
        taxista.setHojaDelincuencia(true);
        taxista.setEstrellas(5);
        taxista.setJustificacion("Cobro de más a un cliente");
        taxista.setUsuarioByPkCorreoUsuario(usuario);

        when(taxistasRepositorio.existsById("taxistaSuspendido@taxista.com")).thenReturn(true);
        when(taxistasRepositorio.findById("taxistaSuspendido@taxista.com")).thenReturn(Optional.of(taxista));

        Map<String, Object> estado = taxistasServicio.obtenerEstado("taxistaSuspendido@taxista.com");

        assertTrue(estado.get("estado").equals(false));
        assertTrue(estado.get("justificacion").equals("Cobro de más a un cliente"));
    }
}