package com.coopetico.coopeticobackend.servicios.unit;

/**
 Pruebas de unidad del UsuarioServicio
 @author      Hannia Aguilar Salas
 @since       21-04-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.servicios.UsuarioServicio;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UsuarioServicioUnitTest {
    /**
    UsuarioEntidad agregarUsuario(UsuarioEntidad usuarioSinGrupo, String grupoId);
    UsuarioEntidad crearUsuario(UsuarioEntidad usuarioEntidad);
    Optional<UsuarioEntidad> usuarioPorCorreo(String correo);
    List<String> obtenerPermisos(UsuarioEntidad usuario);
    List<UsuarioEntidad> obtenerUsuarios();
    Page<UsuarioEntidad> obtenerUsuarios(Pageable pageable);
    List<UsuarioEntidad> obtenerUsuariosPorGrupo(GrupoEntidad grupo);
    void eliminar(String correo);
     */

    @Autowired
    UsuarioServicio servicio;

    @Test
    public void testListarUsuarios(){
        List<UsuarioEntidad> listaUsuarios = servicio.obtenerUsuarios();
        Assert.assertEquals(7, listaUsuarios.size());
    }

//    @
//    public void agregarUsuario(){
//        UsuarioEntidad usuarioNuevo = new UsuarioEntidad();
//        UsuarioEntidad usuarioAgregado = servicio.crearUsuario(usuarioNuevo);
//    }

}
