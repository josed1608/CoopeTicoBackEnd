package com.coopetico.coopeticobackend.servicios.unit;

/**
 Test de unidad del PermisoGrupoServicio
 @author      Jefferson Alvarez
 @since       18-04-2019
 @version:    1.0
 */


import com.coopetico.coopeticobackend.entidades.GrupoEntidad;
import com.coopetico.coopeticobackend.entidades.PermisoEntidad;
import com.coopetico.coopeticobackend.entidades.PermisosGrupoEntidad;
import com.coopetico.coopeticobackend.entidades.PermisosGrupoEntidadPK;
import com.coopetico.coopeticobackend.repositorios.PermisosGruposRepositorio;
import com.coopetico.coopeticobackend.servicios.GrupoServicio;
import com.coopetico.coopeticobackend.servicios.PermisoGrupoServicioImpl;
import com.coopetico.coopeticobackend.servicios.PermisosServicio;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class PermisoGrupoServicioUnitTest {

    @Autowired
    protected WebApplicationContext wac;


    @MockBean
    GrupoServicio grupoServicio;

    @MockBean
    PermisosServicio permisosServicio;

    @MockBean
    PermisosGruposRepositorio permisosGruposRepositorio;

    @InjectMocks
    PermisoGrupoServicioImpl permisoGrupoServicio;

    @Before
    public void setUp() {
        permisosGruposRepositorio = mock(PermisosGruposRepositorio.class);
        permisoGrupoServicio = new PermisoGrupoServicioImpl(permisosGruposRepositorio);
        MockitoAnnotations.initMocks( this );
    }

    @Test
    public void testObtenerPermisosGrupo() throws Exception {

        GrupoEntidad grupoEntidad = new GrupoEntidad("Cliente", null,null);
        PermisoEntidad permisoEntidad = new PermisoEntidad(100, "Pedir viaje", null);
        PermisoEntidad permisoEntidad2 = new PermisoEntidad(101, "Ver taxis cerca", null);

        List<PermisoEntidad> entidades = Arrays.asList(permisoEntidad, permisoEntidad2);
        when(permisosGruposRepositorio.findPermisosGrupo(grupoEntidad)).thenReturn(entidades);

        List<PermisoEntidad> listaPermisos = permisoGrupoServicio.getPermisosGrupo(grupoEntidad);
        assertTrue(listaPermisos.size() == 2);
    }

    @Test
    public void testObtenerPermisosGrupoPorPK() throws Exception {

        GrupoEntidad grupoEntidad = new GrupoEntidad("Cliente", null,null);
        PermisoEntidad permisoEntidad = new PermisoEntidad(300, "Agregar Taxi", null);
        PermisosGrupoEntidadPK permisosGrupoEntidadPK = new PermisosGrupoEntidadPK(300, "Cliente");

        PermisosGrupoEntidad permisosGrupoEntidad = new PermisosGrupoEntidad(permisosGrupoEntidadPK, permisoEntidad, grupoEntidad);

        when(permisosGruposRepositorio.findById(permisosGrupoEntidadPK)).thenReturn(Optional.of(permisosGrupoEntidad));

        PermisosGrupoEntidad permisosGrupoEntidad1 = permisoGrupoServicio.getPermisoGrupoPorPK(permisosGrupoEntidadPK);

        assertNotNull(permisosGrupoEntidad1);
        assertTrue(permisosGrupoEntidad1.getPermisoByPkIdPermisos().getPkId() == permisoEntidad.getPkId());
        assertTrue(permisosGrupoEntidad1.getGrupoByPkIdGrupo().getPkId() == grupoEntidad.getPkId());

    }

    @Test
    public void testGuardarPermisoGrupo() throws Exception {
        GrupoEntidad grupoEntidad = new GrupoEntidad("Cliente", null,null);
        PermisoEntidad permisoEntidad = new PermisoEntidad(300, "Agregar Taxi", null);
        PermisosGrupoEntidadPK permisosGrupoEntidadPK = new PermisosGrupoEntidadPK(300, "Cliente");

        PermisosGrupoEntidad permisosGrupoEntidad = new PermisosGrupoEntidad(permisosGrupoEntidadPK, permisoEntidad, grupoEntidad);

        when(permisosGruposRepositorio.save(permisosGrupoEntidad)).thenReturn(permisosGrupoEntidad);

        boolean retorno = permisoGrupoServicio.guardarPermisosGrupo(permisosGrupoEntidad);

        assertTrue(retorno);
    }

    @Test
    public void testEliminarPermisosGrupo() throws Exception {
        GrupoEntidad grupoEntidad = new GrupoEntidad("Cliente", null,null);
        PermisoEntidad permisoEntidad = new PermisoEntidad(300, "Agregar Taxi", null);
        PermisosGrupoEntidadPK permisosGrupoEntidadPK = new PermisosGrupoEntidadPK(300, "Cliente");

        PermisosGrupoEntidad permisosGrupoEntidad = new PermisosGrupoEntidad(permisosGrupoEntidadPK, permisoEntidad, grupoEntidad);

        boolean retorno = permisoGrupoServicio.guardarPermisosGrupo(permisosGrupoEntidad);

        assertTrue(retorno);

    }

}
