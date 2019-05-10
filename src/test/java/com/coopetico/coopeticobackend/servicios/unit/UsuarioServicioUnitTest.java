package com.coopetico.coopeticobackend.servicios.unit;

/**
 Pruebas de unidad del UsuarioServicio
 @author      Hannia Aguilar Salas
 @since       21-04-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.entidades.GrupoEntidad;
import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.servicios.UsuarioServicio;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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

    @Test
    public void testAgregarUsuario(){
        UsuarioEntidad usuarioNuevo = getUsuarioDefecto();
        UsuarioEntidad usuarioAgregado = servicio.crearUsuario(usuarioNuevo);
        assertNotNull(usuarioAgregado);
        servicio.eliminar(getUsuarioDefecto().getPkCorreo());
    }

    @Test
    public void testUsuarioPorCorreo(){
        servicio.crearUsuario(getUsuarioDefecto());
        Optional<UsuarioEntidad> usuario = servicio.usuarioPorCorreo(getUsuarioDefecto().getPkCorreo());
        assertNotNull(usuario.get());
        servicio.eliminar(getUsuarioDefecto().getPkCorreo());
        testListarUsuarios();
    }

    @Test
    public void testEliminarUsuario(){
        servicio.crearUsuario(getUsuarioDefecto());
        servicio.eliminar(getUsuarioDefecto().getPkCorreo());
        testListarUsuarios();
    }

    @Ignore
    public static UsuarioEntidad getUsuarioDefecto(){
        UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
        usuarioEntidad.setFoto("");
        usuarioEntidad.setContrasena("aguacatico");
        usuarioEntidad.setPkCorreo("test@test.com");
        usuarioEntidad.setNombre("testNombre");
        usuarioEntidad.setApellido1("test1");
        usuarioEntidad.setApellido2("test2");
        usuarioEntidad.setGrupoByIdGrupo(new GrupoEntidad());
        usuarioEntidad.getGrupoByIdGrupo().setPkId("Administrativo");
        usuarioEntidad.setTelefono("88887777");
        return usuarioEntidad;
    }


    @Test
    public void testCambiarEstado(){
        UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
        usuarioEntidad.setFoto("");
        usuarioEntidad.setContrasena("aguacatico");
        usuarioEntidad.setPkCorreo("test@test.com");
        usuarioEntidad.setNombre("testNombre");
        usuarioEntidad.setApellido1("test1");
        usuarioEntidad.setApellido2("test2");
        usuarioEntidad.setGrupoByIdGrupo(new GrupoEntidad());
        usuarioEntidad.getGrupoByIdGrupo().setPkId("Administrativo");
        usuarioEntidad.setTelefono("88887777");
        usuarioEntidad.setValid(true);

       // when();
    }

}
