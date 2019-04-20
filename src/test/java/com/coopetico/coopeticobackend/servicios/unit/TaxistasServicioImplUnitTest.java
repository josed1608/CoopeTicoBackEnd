package com.coopetico.coopeticobackend.servicios.unit;

/**
 Test de unidad del PermisoServicio
 @author      Christofer Rodriguez
 @since       19-04-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.entidades.TaxiEntidad;
import com.coopetico.coopeticobackend.entidades.TaxistaEntidad;
import com.coopetico.coopeticobackend.entidades.TaxistaEntidadTemporal;
import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.repositorios.GruposRepositorio;
import com.coopetico.coopeticobackend.repositorios.TaxisRepositorio;
import com.coopetico.coopeticobackend.repositorios.TaxistasRepositorio;
import com.coopetico.coopeticobackend.repositorios.UsuariosRepositorio;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Pruebas para el servicio de taxistas.
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
     * Repositorio de grupos.
     */
    @MockBean
    GruposRepositorio gruposRepositorio;

    /**
     * Repositorio de usuarios.
     */
    @MockBean
    UsuariosRepositorio usuarioRepositorio;

    /**
     * Repositorio de taxis.
     */
    @MockBean
    TaxisRepositorio taxiRepositorio;

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
        taxistasRepositorio = mock(TaxistasRepositorio.class);
        gruposRepositorio = mock(GruposRepositorio.class) ;
        usuarioRepositorio = mock(UsuariosRepositorio.class);
        taxiRepositorio = mock(TaxisRepositorio.class);

        taxistasServicio = new TaxistasServicioImpl(gruposRepositorio,usuarioRepositorio,taxiRepositorio);
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
        usuario1.setApellidos("Apellidos1");
        usuario1.setPkCorreo("taxistaMoka1@coopetico.com");
        usuario1.setTelefono("22333322");
        usuario1.setFoto("foto");
        TaxiEntidad taxi1 = new TaxiEntidad();
        taxi1.setPkPlaca("AAA111");
        TaxistaEntidad taxistaEntidad1 = new TaxistaEntidad();
        taxistaEntidad1.setPkCorreoUsuario("taxistaMoka1@coopetico.com");
        taxistaEntidad1.setFaltas("0");
        taxistaEntidad1.setEstado(true);
        taxistaEntidad1.setHojaDelincuencia(true);
        taxistaEntidad1.setEstrellas(5);
        taxistaEntidad1.setTaxiByPlacaTaxiManeja(taxi1);
        taxistaEntidad1.setUsuarioByPkCorreoUsuario(usuario1);
        //Segundo taxista
        UsuarioEntidad usuario2 = new UsuarioEntidad();
        usuario2.setNombre("Taxista1");
        usuario2.setApellidos("Apellidos1");
        usuario2.setPkCorreo("taxistaMoka1@coopetico.com");
        usuario2.setTelefono("22333322");
        usuario2.setFoto("foto");
        TaxiEntidad taxi2 = new TaxiEntidad();
        taxi2.setPkPlaca("AAA111");
        TaxistaEntidad taxistaEntidad2 = new TaxistaEntidad();
        taxistaEntidad2.setPkCorreoUsuario("taxistaMoka1@coopetico.com");
        taxistaEntidad2.setFaltas("0");
        taxistaEntidad2.setEstado(true);
        taxistaEntidad2.setHojaDelincuencia(true);
        taxistaEntidad2.setEstrellas(5);
        taxistaEntidad2.setTaxiByPlacaTaxiManeja(taxi2);
        taxistaEntidad2.setUsuarioByPkCorreoUsuario(usuario2);
        // Se le indica al mock que retorne esa lista cuando consultan al repo
        List<TaxistaEntidad> entidades = Arrays.asList(taxistaEntidad1, taxistaEntidad2);
        when(taxistasRepositorio.findAll()).thenReturn(entidades);
        // Se piden los taxistas al servicio
        List<TaxistaEntidadTemporal> entidadesServicio = taxistasServicio.consultar();
        //Se compara que no sea nulo
        assertNotNull(entidadesServicio);
        //Se comprueba que contengan 2 taxistas
        assertTrue(entidadesServicio.size() == 2);
    }

    /**
     * Prueba de unidad de consultar un taxista en especifico.
     */
    @Test
    public void testConsultarPorId() throws Exception {
        //Primer taxista
        UsuarioEntidad usuario = new UsuarioEntidad();
        usuario.setNombre("Taxista1");
        usuario.setApellidos("Apellidos1");
        usuario.setPkCorreo("taxistaMoka1@coopetico.com");
        usuario.setTelefono("22333322");
        usuario.setFoto("foto");
        TaxiEntidad taxi = new TaxiEntidad();
        taxi.setPkPlaca("AAA111");
        TaxistaEntidad taxistaEntidad1 = new TaxistaEntidad();
        taxistaEntidad1.setPkCorreoUsuario("taxistaMoka1@coopetico.com");
        taxistaEntidad1.setFaltas("0");
        taxistaEntidad1.setEstado(true);
        taxistaEntidad1.setHojaDelincuencia(true);
        taxistaEntidad1.setEstrellas(5);
        taxistaEntidad1.setTaxiByPlacaTaxiManeja(taxi);
        taxistaEntidad1.setUsuarioByPkCorreoUsuario(usuario);
        //Se le indica al mock que cuando pregunten al repo por ese taxista que retorne ese taxista.
        when(taxistasRepositorio.findById("taxistaMoka1@coopetico.com")).thenReturn(Optional.of(taxistaEntidad1));
        // Se le pide el taxista al servicio
        TaxistaEntidadTemporal entidadRetornada = taxistasServicio.consultarPorId("taxistaMoka1@coopetico.com");
        //Se compara que no sea nulo
        assertNotNull(entidadRetornada);
        //Se compara que sea el taxista solicitado
        assertTrue(entidadRetornada.getPkCorreoUsuario().equals("taxistaMoka1@coopetico.com"));
    }

}