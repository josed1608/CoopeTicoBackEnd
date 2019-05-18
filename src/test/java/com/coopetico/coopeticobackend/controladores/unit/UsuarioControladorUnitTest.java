package com.coopetico.coopeticobackend.controladores.unit;

import com.coopetico.coopeticobackend.controladores.UsuarioControlador;
import com.coopetico.coopeticobackend.entidades.bd.GrupoEntidad;
import com.coopetico.coopeticobackend.entidades.bd.UsuarioEntidad;
import com.coopetico.coopeticobackend.entidades.UsuarioTemporal;
import com.coopetico.coopeticobackend.mail.EmailService;
import com.coopetico.coopeticobackend.servicios.ClienteServicio;
import com.coopetico.coopeticobackend.entidades.bd.UsuarioEntidad;
import com.coopetico.coopeticobackend.servicios.UsuarioServicio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UsuarioControladorUnitTest {

    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    UsuarioControlador usuarioControlador;

    @MockBean
    UsuarioServicio usuarioServicio;

    @MockBean
    EmailService emailService;


    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(usuarioControlador).build();
    }


    /**
     * Test de crear usuario
     * @throws Exception Lanza exepcion en caso de fallo
     */
    @Test
    public void testCrearUsuario() throws Exception {
        doAnswer((i)->{return UsuarioEntidad.class;}).when(usuarioServicio).crearUsuario(any(UsuarioEntidad.class));

        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getUsuarioJSON()))
                .andExpect(status().isOk());

    }


    /**
     * Test de obtener usuarios
     * @throws Exception Lanza exepcion en caso de algún error
     */
    @Test
    public void testObtenerUsuarios() throws Exception {
        String url = "/usuarios";

        List<UsuarioEntidad> usuarios = obtenerListaUsuarios();
        given(usuarioServicio.obtenerUsuarios()).willReturn(usuarios);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        UsuarioTemporal[] listaGrupos = objectMapper.readValue(content, UsuarioTemporal[].class);
        assertTrue(listaGrupos.length == 10);
    }


    /**
     * Metodo que retorna el JSON de un usuario
     * @return String JSON del usuario
     */
    public static String getUsuarioJSON(){
        return "{"+
                "\"pkCorreo\": \"gerente11@gerente.com\","+
                "\"nombre\": \"Gerente\","+
                "\"apellidos\": \"apellido\","+
                "\"telefono\": \"11111111\","+
                "\"contrasena\": \"$2a$10$gJ0hUnsEvTp5zyBVo19IHe.GoYKkL3Wy268wGJxG5.k.tUFhSUify\","+
                "\"foto\": \"foto\","+
                "\"clienteByPkCorreo\": null,"+
                "\"coopeticoByPkCorreo\": null,"+
                "\"taxistaByPkCorreo\": null,"+
                "\"grupoByIdGrupo\": {"+
                "\"pkId\": \"Gerente\""+
                "}";
    }


    /**
     * Metodo para obtener un usuario para las pruebas
     * @return
     */
    public static UsuarioTemporal getUsuarioTemporal(){
        UsuarioTemporal usuarioTemporal = new UsuarioTemporal();
        usuarioTemporal.setCorreo("gerente11@gerente.com");
        usuarioTemporal.setNombre("Nombre");
        usuarioTemporal.setApellido1("Apellido1");
        usuarioTemporal.setApellido2("Apellido2");
        usuarioTemporal.setCorreo("a@ad.com");
        usuarioTemporal.setTelefono("88884444");
        usuarioTemporal.setIdGrupo("Cliente");
        return usuarioTemporal;
    }

    /**
     * Metodo que convierte una entidad a JSON
     * @param usuarioEntidad Entidad a convertir
     * @return String con el JSON de la entidad
     */
    public String convertirAJson(UsuarioEntidad usuarioEntidad){
        Gson gson = new Gson();
        return gson.toJson(usuarioEntidad);
    }

    /**
     * Metodo para obtener lista de usuarios.
     * @return Lista con usuarios
     */
    public static List<UsuarioEntidad> obtenerListaUsuarios(){
        List<UsuarioEntidad> usuarios = new ArrayList<>();
        for (int i = 0; i < 10; ++i){
            usuarios.add(getUsuarioTemporal().convertirAUsuarioEntidad());
        }
        return usuarios;
    }
    /**
     * @Autowired
     *     private TokensRecuperacionContrasenaServicioImpl tokensServicio;
     *     @Autowired
     *     private EmailServiceImpl mail;
     *     private UsuariosRepositorio usuariosRepositorio;
     *     private PasswordEncoder encoder;
     *     private UsuarioServicio usuarioServicio;
     *
    public UsuarioControlador(UsuariosRepositorio usuariosRepositorio, PasswordEncoder encoder, TokensRecuperacionContrasenaServicio tokensRecuperacionContrasenaServicio, UsuarioServicio servicio, EmailServiceImpl mail)
    public boolean recuperarContrasena(@PathVariable String correo)
    public boolean cambiarContrasena(@RequestBody AuthenticationRequest datosUsuario)
    public boolean validarTokenRecuperarContrasena(@PathVariable String id, @PathVariable String token)
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody UsuarioTemporal usuario, BindingResult resultado)
    public List<UsuarioTemporal> obtenerUsuarios()
    public Page<UsuarioTemporal> obtenerUsuarios(@PathVariable Integer pagina)
    public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable String id)
    public ResponseEntity<?> eliminarUsuarioPorId(@PathVariable String id)
    public List<UsuarioTemporal> obtenerUsuariosPorGrupo(@RequestBody GrupoEntidad grupoEntidad)
    public ResponseEntity<?> actualizar(@Valid @RequestBody UsuarioTemporal usuario, @PathVariable String id, BindingResult resultado)
    public boolean validarTokenRecuperarContrasena(String id)
    public ResponseEntity<?> subirImagen(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") String id)
    public void eliminarFoto(String nombreFotoAnterior)
    public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto)
     */
}

