package com.coopetico.coopeticobackend.servicios.integration;

/**
 Test de integracion del PermisosGrupoServicio
 @author      Jefferson Alvarez
 @since       18-04-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.entidades.GrupoEntidad;
import com.coopetico.coopeticobackend.entidades.PermisoEntidad;
import com.coopetico.coopeticobackend.entidades.PermisosGrupoEntidad;
import com.coopetico.coopeticobackend.entidades.PermisosGrupoEntidadPK;
import com.coopetico.coopeticobackend.repositorios.GruposRepositorio;
import com.coopetico.coopeticobackend.repositorios.PermisosGruposRepositorio;
import com.coopetico.coopeticobackend.repositorios.PermisosRepositorio;
import com.coopetico.coopeticobackend.servicios.GrupoServicio;
import com.coopetico.coopeticobackend.servicios.PermisoGrupoServicio;
import com.coopetico.coopeticobackend.servicios.PermisosServicio;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PermisoGrupoServicioIntegrationTest {

    @Autowired
    protected WebApplicationContext wac;


    @Autowired
    PermisoGrupoServicio permisoGrupoServicio;

    @Autowired
    GrupoServicio grupoServicio;

    @Autowired
    PermisosServicio permisosServicio;

    @Autowired
    PermisosGruposRepositorio permisosGruposRepositorio;

    @Autowired
    GruposRepositorio gruposRepositorios;

    @Autowired
    PermisosRepositorio permisosRepositorio;


    @Test
    @Transactional
    public void testObtenerPermisosGrupo() throws Exception {

        GrupoEntidad grupoEntidad = new GrupoEntidad("Cliente", null,null);
        PermisoEntidad permisoEntidad = new PermisoEntidad(100, "Pedir viaje", null);
        PermisosGrupoEntidadPK permisosGrupoEntidadPK = new PermisosGrupoEntidadPK(100, "Cliente");

        permisosRepositorio.save(permisoEntidad);
        gruposRepositorios.save(grupoEntidad);

        PermisosGrupoEntidad permisosGrupoEntidad = new PermisosGrupoEntidad(permisosGrupoEntidadPK, permisoEntidad, grupoEntidad);
        permisosGruposRepositorio.save(permisosGrupoEntidad);

        List<PermisoEntidad> permisos = permisoGrupoServicio.getPermisosGrupo(grupoEntidad);

        assertNotNull(permisos);
        assertTrue(permisos.size() == 1);
    }

    @Test
    @Transactional
    public void testObtenerPermisosGrupoPorPK() throws Exception {

        GrupoEntidad grupoEntidad = new GrupoEntidad("Cliente", null,null);
        PermisoEntidad permisoEntidad = new PermisoEntidad(100, "Pedir viaje", null);
        PermisosGrupoEntidadPK permisosGrupoEntidadPK = new PermisosGrupoEntidadPK(100, "Cliente");

        permisosRepositorio.save(permisoEntidad);
        gruposRepositorios.save(grupoEntidad);

        PermisosGrupoEntidad permisosGrupoEntidad = new PermisosGrupoEntidad(permisosGrupoEntidadPK, permisoEntidad, grupoEntidad);
        permisosGruposRepositorio.save(permisosGrupoEntidad);

        PermisosGrupoEntidad permisosGrupoEntidadRetorno = permisoGrupoServicio.getPermisoGrupoPorPK(permisosGrupoEntidadPK);

        assertNotNull(permisosGrupoEntidadRetorno);
        assertTrue(permisosGrupoEntidadRetorno.getPermisoByPkIdPermisos().getPkId() == permisoEntidad.getPkId());
        assertTrue(permisosGrupoEntidadRetorno.getGrupoByPkIdGrupo().getPkId() == grupoEntidad.getPkId());

    }

    @Test
    @Transactional
    public void testGuardarPermisoGrupo() throws Exception {
        GrupoEntidad grupoEntidad = new GrupoEntidad("Cliente", null,null);
        PermisoEntidad permisoEntidad = new PermisoEntidad(100, "Pedir viaje", null);
        PermisosGrupoEntidadPK permisosGrupoEntidadPK = new PermisosGrupoEntidadPK(100, "Cliente");

        permisosRepositorio.save(permisoEntidad);
        gruposRepositorios.save(grupoEntidad);

        PermisosGrupoEntidad permisosGrupoEntidad = new PermisosGrupoEntidad(permisosGrupoEntidadPK, permisoEntidad, grupoEntidad);

        boolean retorno = permisoGrupoServicio.guardarPermisosGrupo(permisosGrupoEntidad);

        assertTrue(retorno);
    }

    @Test
    @Transactional
    public void testEliminarPermisosGrupo() throws Exception {
        GrupoEntidad grupoEntidad = new GrupoEntidad("Cliente", null,null);
        PermisoEntidad permisoEntidad = new PermisoEntidad(100, "Pedir viaje", null);
        PermisosGrupoEntidadPK permisosGrupoEntidadPK = new PermisosGrupoEntidadPK(100, "Cliente");

        permisosRepositorio.save(permisoEntidad);
        gruposRepositorios.save(grupoEntidad);

        PermisosGrupoEntidad permisosGrupoEntidad = new PermisosGrupoEntidad(permisosGrupoEntidadPK, permisoEntidad, grupoEntidad);
        permisosGruposRepositorio.save(permisosGrupoEntidad);

        boolean retorno = permisoGrupoServicio.eliminarPermisosGrupo(permisosGrupoEntidad);

        assertTrue(retorno);

    }

}
