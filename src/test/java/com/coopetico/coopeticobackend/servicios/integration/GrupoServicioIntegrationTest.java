package com.coopetico.coopeticobackend.servicios.integration;

/**
 Test de integracion del GrupoServicio
 @author      Jefferson Alvarez
 @since       18-04-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.entidades.GrupoEntidad;
import com.coopetico.coopeticobackend.repositorios.GruposRepositorio;
import com.coopetico.coopeticobackend.servicios.GrupoServicio;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GrupoServicioIntegrationTest {

    @Autowired
    GrupoServicio grupoServicio;

    @Autowired
    GruposRepositorio gruposRepositorio;


    @Test
    @Transactional
    public void obtenerGrupos() throws Exception {
        //IMPORTANTE
        //Las pruebas se hacen con la tabla de grupos vacia

        GrupoEntidad grupoEntidad = new GrupoEntidad("Administrativo",null, null);
        gruposRepositorio.save(grupoEntidad);

        GrupoEntidad grupoEntidad2 = new GrupoEntidad("Cliente",null, null);
        gruposRepositorio.save(grupoEntidad2);

        //Act
        List<GrupoEntidad> grupos = grupoServicio.getGrupos();

        //Assert
        //Las pruebas se hacen con la tabla de permisos vacia
        assertTrue(grupos != null);
        assertTrue(grupos.size() == 2);

    }

    @Test
    @Transactional
    public void obtenerGrupo() throws Exception {
        //IMPORTANTE
        //Las pruebas se hacen con la tabla de grupos vacia

        GrupoEntidad grupoEntidad = new GrupoEntidad("Administrativo", null, null);
        gruposRepositorio.save(grupoEntidad);

        //Act
        GrupoEntidad grupo = grupoServicio.getGrupoPorPK("Administrativo");

        //Assert
        assertNotNull(grupo);
        assertTrue(grupo.getPkId() == grupoEntidad.getPkId());

    }
}
