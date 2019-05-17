package com.coopetico.coopeticobackend.controladores;

import com.coopetico.coopeticobackend.entidades.*;
import com.coopetico.coopeticobackend.entidades.bd.GrupoEntidad;
import com.coopetico.coopeticobackend.entidades.bd.TokenRecuperacionContrasenaEntidad;
import com.coopetico.coopeticobackend.mail.EmailServiceImpl;
import com.coopetico.coopeticobackend.repositorios.UsuariosRepositorio;
import com.coopetico.coopeticobackend.servicios.TokensRecuperacionContrasenaServicioImpl;
import com.coopetico.coopeticobackend.servicios.UsuarioServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.coopetico.coopeticobackend.entidades.bd.UsuarioEntidad;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 Controlador de usuarios, hice los métodos de actualizarContrasena: Cambiar la contraseña en la base de datos
 y mostrarInterfazCambioContrasena: Validar el token que se envío al mail del usuario para el cambio de contraseña.
 @author      Hannia Aguilar Salas
 @since       19-04-2019
 @version:   3.0
 */

@CrossOrigin( origins = {"http://localhost:4200"})
@RestController
@RequestMapping(path="/usuarios")
@Validated
public class UsuarioControlador {

    private final TokensRecuperacionContrasenaServicioImpl tokensServicio;
    private final EmailServiceImpl mail;
    private UsuariosRepositorio usuariosRepositorio;
    private PasswordEncoder encoder;
    private UsuarioServicio usuarioServicio;
    private UsuarioTemporal usuarioTemporal;
    private final Logger log = LoggerFactory.getLogger(UsuarioControlador.class);
    private UtilidadesControlador utilidadesControlador;


    public UsuarioControlador(UsuariosRepositorio usuariosRepositorio, PasswordEncoder encoder,UsuarioServicio servicio, TokensRecuperacionContrasenaServicioImpl tokensServicio, EmailServiceImpl mail) {
        this.usuarioServicio = servicio;
        this.usuariosRepositorio = usuariosRepositorio;
        this.encoder = encoder;
        this.usuarioTemporal = new UsuarioTemporal();
        this.utilidadesControlador = new UtilidadesControlador();
        this.tokensServicio = tokensServicio;
        this.mail = mail;
    }

    /**
     * Método que crea un nuevo token de cambio de contraseña para el usuario, lo ingresa en la base de datos y envía el email.
     * @param correo Correo del usuario
     * @return El JWT en caso de un éxito o fallo.
     */
    @GetMapping(path="/recuperarContrasena/{correo}")
    public boolean recuperarContrasena (@PathVariable String correo) {
        String token = tokensServicio.insertarToken(correo);
        if (token == null) {
            return false;
        }
        mail.enviarCorreoRecuperarContrasena(correo, token);
        return true;
    }

    /**
     *  Método que recibe el id (correo) y la nueva contraseña
     * @param datosUsuario Modelo del request de autenticación. Espera los atributos username y password
     * @return El JWT en caso de un cambio exitoso o fallido.
     */
    @PutMapping(path = "/cambiarContrasena")
    @ResponseStatus(HttpStatus.OK)
    public boolean cambiarContrasena(@RequestBody AuthenticationRequest datosUsuario){
        //Obtener el usuario con ese nombre o correo
        if(datosUsuario.getUsername() != null & datosUsuario.getPassword() != null) {

            if (this.usuarioServicio.usuarioPorCorreo(datosUsuario.getUsername()).isPresent()) {
                UsuarioEntidad usuarioEntidad = this.usuarioServicio.usuarioPorCorreo(datosUsuario.getUsername()).get();
                //Encriptar la contraseña
                usuarioEntidad.setContrasena(encoder.encode(datosUsuario.getPassword()));
                //Actualizar el usuario
                usuariosRepositorio.save(usuarioEntidad);
                // Se borra de la tabla el Token
                tokensServicio.eliminarToken(datosUsuario.getUsername());
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Método para validar el token, revisa que exista un token para ese usuario (correo) y que la fecha del link no haya expirado.
     * @param id Correo del usuario
     * @param token Token del link de recuperación.
     * @return Boolean que indica si es válido o no.
     */
    @GetMapping("/cambiarContrasena/{id}/{token}")
    @ResponseStatus(HttpStatus.OK)
    public boolean validarTokenRecuperarContrasena(@PathVariable String id, @PathVariable String token) {
        TokenRecuperacionContrasenaEntidad tokenContrasena = tokensServicio.getToken(id);
        //Validación con el usuario
        if (tokenContrasena == null || !tokenContrasena.getFkCorreoUsuario().equals(id) || !tokenContrasena.getToken().equals(token)) {
            return false;
        }

        //Revisa la fecha del link
        Calendar calendario = Calendar.getInstance();
        return (tokenContrasena.getFechaExpiracion()
                .getTime() - calendario.getTime()
                .getTime()) > 0;
    }

    /**
     * Metodo que se encarga de crear un usuario
     * @param usuario Usuario nuevo a crear
     * @param resultado Valida que usuario tenga un formato correcto
     * @return El un hashmap con el nuevo usuario agregado
     */
    @PostMapping()
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody UsuarioTemporal usuario, BindingResult resultado){

        Map<String, Object> response = new HashMap<>();
        UsuarioEntidad usuarioEntidad;

        if (resultado.hasErrors()) {
            List<String> errores = resultado.getFieldErrors()
                    .stream()
                    .map(error -> "El campo '"+ error.getField()+"' "+error.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errores", errores);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            usuarioEntidad =  usuarioServicio.crearUsuario(usuario.convertirAUsuarioEntidad());
        }catch (DataAccessException e){
            response.put("mensaje", "Error al insertar en la base de datos");
            response.put("error", Objects.requireNonNull(e.getMessage()).concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        UsuarioTemporal usuarioTemporal = new UsuarioTemporal(usuarioEntidad);
        response.put("mensaje", "Usuario creado con éxito");
        response.put("usuario", usuarioTemporal);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Metodo para obtener una lista de usuarios
     * @return Lista de usuarios
     */
    @GetMapping()
    public List<UsuarioTemporal> obtenerUsuarios(){
        List<UsuarioEntidad> usuarios = usuarioServicio.obtenerUsuarios();
        return usuarioTemporal.getListaUsuarioTemporal(usuarios);
    }

    /**
     * Metodo para obtener usuarios por pagina
     * @param pagina Numero de pagina a recuperar
     * @return Retorna los usuarios que contiene la pagina
     */
    @GetMapping("/page/{pagina}")
    public Page<UsuarioTemporal> obtenerUsuarios(@PathVariable Integer pagina){
        int TAMANIO = 4;
        Pageable pageable = PageRequest.of(pagina, TAMANIO);
        Page<UsuarioEntidad> pageUsuario = usuarioServicio.obtenerUsuarios(pageable);
        List<UsuarioEntidad> listaUsuarios = pageUsuario.getContent();
        List<UsuarioTemporal> listaUsuarioTemp = usuarioTemporal.getListaUsuarioTemporal(listaUsuarios);
        return new PageImpl<>(listaUsuarioTemp, pageable, pageUsuario.getTotalElements());
    }


    /**
     * Metodo para obtener un usuario por id
     * @param id Id del usuario
     * @return Usuario identificado por el id
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable String id){
        UsuarioEntidad usuarioEntidad;
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<UsuarioEntidad> optionalUsuarioEntidad = usuarioServicio.usuarioPorCorreo(id);
            usuarioEntidad = optionalUsuarioEntidad.orElse(null);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al realizar consulta en la base de datos");
            response.put("error", Objects.requireNonNull(e.getMessage()).concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if( usuarioEntidad == null){
            response.put("mensaje", "El usuario ID: ".concat(id.concat(" no existe en la base de datos")));
            response.put("usuario", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        UsuarioTemporal usuario = new UsuarioTemporal(usuarioEntidad);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    /**
     * Metodo para eliminar un usuario por el id
     * @param id Id del usuario
     * @return Mensaje de exito o error
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuarioPorId(@PathVariable String id){
        Map<String, Object> response = new HashMap<>();
        try {
            UsuarioEntidad usuarioEntidad = null;
            if(usuarioServicio.usuarioPorCorreo(id).isPresent()) {
                usuarioEntidad = usuarioServicio.usuarioPorCorreo(id).get();
            }
            assert usuarioEntidad != null;
            this.utilidadesControlador.eliminarFoto(usuarioEntidad.getFoto());
            usuarioServicio.eliminar(id);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al eliminar usuario en la base de datos");
            response.put("error", Objects.requireNonNull(e.getMessage()).concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "Usuario eliminado con éxito");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * Metodo para obtener los usuarios por un determinado grupo
     * @param grupoEntidad Entidad a buscar
     * @return Lista de usuarios del grupo
     */
    @GetMapping("/grupo")
    public List<UsuarioTemporal> obtenerUsuariosPorGrupo(@RequestBody GrupoEntidad grupoEntidad){
        List<UsuarioEntidad> usuarios = usuarioServicio.obtenerUsuariosPorGrupo(grupoEntidad);
        return usuarioTemporal.getListaUsuarioTemporal(usuarios);
    }

    /**
     * Metodo para actualizar un usuario
     * @param usuario usuario nuevo
     * @param id identificador del usuario a actualizar
     * @param resultado Valida si el usuario tiene el formato correcto
     * @return Usuario modificado
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@Valid @RequestBody UsuarioTemporal usuario, @PathVariable String id, BindingResult resultado){
        UsuarioEntidad usuarioEntidad;
        UsuarioEntidad temporal;
        Map<String, Object> response = new HashMap<>();

        if (resultado.hasErrors()) {
            List<String> errores = resultado.getFieldErrors()
                    .stream()
                    .map(error -> "El campo '"+ error.getField()+"' "+error.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errores", errores);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<UsuarioEntidad> optionalUsuarioEntidad= this.usuarioServicio.usuarioPorCorreo(id);
        usuarioEntidad = optionalUsuarioEntidad.orElse(null);

        if( usuarioEntidad == null){
            response.put("mensaje", "Error: no se pudo editar, el usuario ID: ".concat(id.concat(" no existe en la base de datos")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        try {
            usuarioEntidad.setApellido1(usuario.getApellido1());
            usuarioEntidad.setApellido2(usuario.getApellido2());
            usuarioEntidad.setNombre(usuario.getNombre());
            usuarioEntidad.setTelefono(usuario.getTelefono());
            usuarioEntidad.setFoto(usuario.getFoto());

            temporal = usuarioServicio.crearUsuario(usuarioEntidad);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al actualizar usuario en la base de datos");
            response.put("error", Objects.requireNonNull(e.getMessage()).concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Usuario actualizado con éxito");
        response.put("usuario", new UsuarioTemporal(temporal));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Metodo para subir imagen
     * @param archivo Archivo a subir
     * @param id Identificador del usuario
     * @return Respuesta correcto o incorrecto y el usuario con la foto agregada
     */
    @PostMapping("/upload")
    public ResponseEntity<?> subirImagen(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") String id){
        Map<String, Object> response = new HashMap<>();

        Optional<UsuarioEntidad> optionalUsuarioEntidad = usuarioServicio.usuarioPorCorreo(id);
        UsuarioEntidad usuarioEntidad;
        usuarioEntidad = optionalUsuarioEntidad.orElse(null);
        if( usuarioEntidad == null) {
            response.put("mensaje", "Error: no se pudo editar, el usuario ID: ".concat(id.concat(" no existe en la base de datos")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        if( !archivo.isEmpty()){
            String nombreArchivo = UUID.randomUUID().toString()+"_" + Objects.requireNonNull(archivo.getOriginalFilename()).replace(" ","");
            Path rutaArchivo = Paths.get("images").resolve(nombreArchivo).toAbsolutePath();
            log.info(rutaArchivo.toString());

            try {
                Files.copy(archivo.getInputStream(), rutaArchivo);
            } catch (IOException e) {
                response.put("mensaje", "Error al subir la imagen del usuario");
                response.put("error", e.getMessage().concat(":").concat(e.getCause().getMessage()));
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            this.utilidadesControlador.eliminarFoto(usuarioEntidad.getFoto());

            usuarioEntidad.setFoto(nombreArchivo);
            UsuarioTemporal usuarioTemporal = new UsuarioTemporal(usuarioServicio.crearUsuario(usuarioEntidad));
            response.put("usuario",usuarioTemporal);
            response.put("mensaje", "Has subido correctamente la imagen "+nombreArchivo);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}