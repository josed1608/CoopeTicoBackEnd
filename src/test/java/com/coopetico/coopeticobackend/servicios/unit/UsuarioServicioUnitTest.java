
/**
 Pruebas de unidad del UsuarioServicio
 @author      Hannia Aguilar Salas
 @since       21-04-2019
 @version:    1.0
 */

package com.coopetico.coopeticobackend.servicios.unit;
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

import static org.junit.Assert.assertNotNull;

/**
 * Clase que prueba el servicio del usuario
 * Usa los datos del import.sql para que estos funcionen
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UsuarioServicioUnitTest {

    @Autowired
    UsuarioServicio servicio;

    /**
     * Test para obtener usuarios
     */
    @Test
    public void testListarUsuarios(){
        List<UsuarioEntidad> listaUsuarios = servicio.obtenerUsuarios();
        Assert.assertEquals(7, listaUsuarios.size());
    }

    /**
     * Test para agregar usuario
     */
    @Test
    public void testAgregarUsuario(){
        UsuarioEntidad usuarioNuevo = getUsuarioDefecto();
        UsuarioEntidad usuarioAgregado = servicio.crearUsuario(usuarioNuevo);
        assertNotNull(usuarioAgregado);
        servicio.eliminar(getUsuarioDefecto().getPkCorreo());
    }

    /**
     * Test para obtener un usuario por correo
     */
    @Test
    public void testUsuarioPorCorreo(){
        servicio.crearUsuario(getUsuarioDefecto());
        Optional<UsuarioEntidad> usuario = servicio.usuarioPorCorreo(getUsuarioDefecto().getPkCorreo());
        assertNotNull(usuario.get());
        servicio.eliminar(getUsuarioDefecto().getPkCorreo());
        testListarUsuarios();
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
     * @return Un usuario por defecto
     */
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

}
