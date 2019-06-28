package com.coopetico.coopeticobackend.servicios.integration;


/**
 Pruebas de integración del UsuarioServicio
 @author      Hannia Aguilar Salas
 @since       21-04-2019
 @version:    1.0
 */

import com.coopetico.coopeticobackend.entidades.bd.GrupoEntidad;
import com.coopetico.coopeticobackend.entidades.bd.UsuarioEntidad;
import com.coopetico.coopeticobackend.servicios.UsuarioServicio;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)

public class UsuarioServicioIntegrationTest {
    @Autowired
    UsuarioServicio servicio;

    /**
     * Test para obtener usuarios
     */
    @Test
    public void testListarUsuarios(){
        List<UsuarioEntidad> listaUsuarios = servicio.obtenerUsuarios();
        Assert.assertEquals(8, listaUsuarios.size());
    }

    /**
     * Test para agregar usuario
     */
    @Test
    public void testAgregarUsuario(){
        UsuarioEntidad usuarioNuevo = getUsuarioDefecto();
        UsuarioEntidad usuarioAgregado = servicio.crearUsuario(usuarioNuevo);
        assertNotNull(usuarioAgregado);
        assertEquals(usuarioAgregado.getPkCorreo(), getUsuarioDefecto().getPkCorreo());
        servicio.eliminar(getUsuarioDefecto().getPkCorreo());
    }

    /**
     * Test para obtener un usuario por correo
     */
    @Test
    public void testUsuarioPorCorreo(){
        String correo = "gerente@gerente.com";
        Optional<UsuarioEntidad> usuario = servicio.usuarioPorCorreo(correo);
        assertNotNull(usuario.get());
        assertEquals(usuario.get().getPkCorreo(), correo);
    }

    /**
     * Test eliminar un usuario\
     */
    @Test
    public void testEliminarUsuario(){
        // Crear usuario
        servicio.crearUsuario(getUsuarioDefecto());
        // Elimina al usuario creado
        servicio.eliminar(getUsuarioDefecto().getPkCorreo());
        testListarUsuarios();
    }

    /**
     * Metodo para obtener un usuario por defecto
     * @return
     */
    @Ignore
    public static UsuarioEntidad getUsuarioDefecto(){
        UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
        usuarioEntidad.setFoto("");
        usuarioEntidad.setContrasena("aguacatico");
        usuarioEntidad.setPkCorreo("gerente@gerente1.com");
        usuarioEntidad.setNombre("testNombre");
        usuarioEntidad.setApellido1("test1");
        usuarioEntidad.setApellido2("test2");
        usuarioEntidad.setGrupoByIdGrupo(new GrupoEntidad());
        usuarioEntidad.getGrupoByIdGrupo().setPkId("Administrativo");
        usuarioEntidad.setTelefono("88887777");
        return usuarioEntidad;
    }

}


