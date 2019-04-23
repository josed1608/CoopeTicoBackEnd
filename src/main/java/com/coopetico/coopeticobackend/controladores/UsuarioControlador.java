package com.coopetico.coopeticobackend.controladores;

/**
 Controlador de usuarios, hice los métodos de actualizarContrasena: Cambiar la contraseña en la base de datos
 y mostrarInterfazCambioContrasena: Validar el token que se envío al mail del usuario para el cambio de contraseña.
 @author      Hannia Aguilar Salas
 @since       19-04-2019
 @version:   3.0
 */


import com.coopetico.coopeticobackend.entidades.GrupoEntidad;
import com.coopetico.coopeticobackend.entidades.TokenRecuperacionContrasenaEntidad;
import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.entidades.UsuarioTemporal;
import com.coopetico.coopeticobackend.mail.EmailServiceImpl;
import com.coopetico.coopeticobackend.repositorios.UsuariosRepositorio;
import com.coopetico.coopeticobackend.servicios.TokensRecuperacionContrasenaServicio;
import com.coopetico.coopeticobackend.servicios.TokensRecuperacionContrasenaServicioImpl;
import com.coopetico.coopeticobackend.servicios.UsuarioServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.repositorios.UsuariosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Email;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin( origins = {"http://localhost:4200"})
@RestController
@RequestMapping(path="/usuarios")
@Validated
public class UsuarioControlador {

    @Autowired
    private TokensRecuperacionContrasenaServicioImpl tokensServicio;
    @Autowired
    private EmailServiceImpl mail;
    private UsuariosRepositorio usuariosRepositorio;
    private PasswordEncoder encoder;
    private UsuarioServicio usuarioServicio;
    private UsuarioTemporal usuarioTemporal;
    private final Integer TAMANIO = 4;
    private final Logger log = LoggerFactory.getLogger(UsuarioControlador.class);

    @Autowired
    public UsuarioControlador(UsuariosRepositorio usuariosRepositorio, PasswordEncoder encoder, TokensRecuperacionContrasenaServicio tokensRecuperacionContrasenaServicio, UsuarioServicio servicio, EmailServiceImpl mail) {
        this.usuarioServicio = servicio;
        this.usuariosRepositorio = usuariosRepositorio;
        this.encoder = encoder;
        this.usuarioTemporal = new UsuarioTemporal();
        //this.tokensRecuperacionContrasenaServicio = tokensRecuperacionContrasenaServicio;
        //this.mail = mail;
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
        if ((tokenContrasena.getFechaExpiracion()
                .getTime() - calendario.getTime()
                .getTime()) <= 0) {
            return false;
        }
        return true;
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
        UsuarioEntidad usuarioEntidad = null;

        if (resultado.hasErrors()) {
            List<String> errores = resultado.getFieldErrors()
                    .stream()
                    .map(error -> "El campo '"+ error.getField()+"' "+error.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errores", errores);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            usuarioEntidad =  usuarioServicio.crearUsuario(usuario.convertirAUsuarioEntidad());
        }catch (DataAccessException e){
            response.put("mensaje", "Error al insertar en la base de datos");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        UsuarioTemporal usuarioTemporal = new UsuarioTemporal(usuarioEntidad);
        response.put("mensaje", "Usuario creado con éxito");
        response.put("usuario", usuarioTemporal);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
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
        Pageable pageable = PageRequest.of(pagina, TAMANIO);
        Page<UsuarioEntidad> pageUsuario = usuarioServicio.obtenerUsuarios(pageable);
        List<UsuarioEntidad> listaUsuarios = pageUsuario.getContent();
        List<UsuarioTemporal> listaUsuarioTemp = usuarioTemporal.getListaUsuarioTemporal(listaUsuarios);
        Page<UsuarioTemporal> pageUsuarioTemp = new PageImpl<>(listaUsuarioTemp, pageable, pageUsuario.getTotalElements());
        return pageUsuarioTemp;
    }


    /**
     * Metodo para obtener un usuario por id
     * @param id Id del usuario
     * @return Usuario identificado por el id
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable String id){
        UsuarioEntidad usuarioEntidad = null;
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<UsuarioEntidad> optionalUsuarioEntidad = usuarioServicio.usuarioPorCorreo(id);
            usuarioEntidad = optionalUsuarioEntidad.orElse(null);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al realizar consulta en la base de datos");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if( usuarioEntidad == null){
            response.put("mensaje", "El usuario ID: ".concat(id.concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        UsuarioTemporal usuario = new UsuarioTemporal(usuarioEntidad);
        return new ResponseEntity<UsuarioTemporal>(usuario, HttpStatus.OK);
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
            UsuarioEntidad usuarioEntidad = usuarioServicio.usuarioPorCorreo(id).get();
            this.eliminarFoto(usuarioEntidad.getFoto());
            usuarioServicio.eliminar(id);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al eliminar usuario en la base de datos");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "Usuario eliminado con éxito");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
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
        UsuarioEntidad usuarioEntidad = null;
        UsuarioEntidad temporal = null;
        Map<String, Object> response = new HashMap<>();

        if (resultado.hasErrors()) {
            List<String> errores = resultado.getFieldErrors()
                    .stream()
                    .map(error -> "El campo '"+ error.getField()+"' "+error.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errores", errores);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<UsuarioEntidad> optionalUsuarioEntidad= this.usuarioServicio.usuarioPorCorreo(id);
        usuarioEntidad = optionalUsuarioEntidad.orElse(null);

        if( usuarioEntidad == null){
            response.put("mensaje", "Error: no se pudo editar, el usuario ID: ".concat(id.concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            usuarioEntidad.setApellidos(usuario.getApellidos());
            usuarioEntidad.setNombre(usuario.getNombre());
            usuarioEntidad.setTelefono(usuario.getTelefono());
            usuarioEntidad.setFoto(usuario.getFoto());

            temporal = usuarioServicio.crearUsuario(usuarioEntidad);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al actualizar usuario en la base de datos");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Usuario actualizado con éxito");
        response.put("usuario", new UsuarioTemporal(temporal));

        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
    }

    /**
     * Método para validar el token, revisa que exista un token para ese usuario (correo) y que la fecha del link no haya expirado.
     * @param id Correo del usuario
     * @return Boolean que indica si es válido o no.
     */
    public boolean validarTokenRecuperarContrasena(String id) {
        TokenRecuperacionContrasenaEntidad tokenContrasena  = tokensServicio.getToken(id);
        //Validación con el usuario
        if (tokenContrasena == null || !tokenContrasena.getFkCorreoUsuario().equals(id)) {
            return false;
        }

        //Revisa la fecha del link
        Calendar calendario = Calendar.getInstance();
        if ((tokenContrasena.getFechaExpiracion()
                .getTime() - calendario.getTime()
                .getTime()) <= 0) {
            return false;
        }

        return true;
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
        UsuarioEntidad usuarioEntidad = null;
        usuarioEntidad = optionalUsuarioEntidad.orElse(null);
        if( usuarioEntidad == null) {
            response.put("mensaje", "Error: no se pudo editar, el usuario ID: ".concat(id.concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        if( !archivo.isEmpty()){
            String nombreArchivo = UUID.randomUUID().toString()+"_" + archivo.getOriginalFilename().replace(" ","");
            Path rutaArchivo = Paths.get("images").resolve(nombreArchivo).toAbsolutePath();
            log.info(rutaArchivo.toString());

            try {
                Files.copy(archivo.getInputStream(), rutaArchivo);
            } catch (IOException e) {
                response.put("mensaje", "Error al subir la imagen del usuario");
                response.put("error", e.getMessage().concat(":").concat(e.getCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            this.eliminarFoto(usuarioEntidad.getFoto());


            usuarioEntidad.setFoto(nombreArchivo);
            UsuarioTemporal usuarioTemporal = new UsuarioTemporal(usuarioServicio.crearUsuario(usuarioEntidad));
            response.put("usuario",usuarioTemporal);
            response.put("mensaje", "Has subido correctamente la imagen "+nombreArchivo);
        }
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
    }


    /**
     * Metodo para eliminar una foto
     * @param nombreFotoAnterior Nombre de la foto  a eliminar
     */
    public void eliminarFoto(String nombreFotoAnterior){
        if( nombreFotoAnterior != null && nombreFotoAnterior.length()>0){
            Path rutaArchivoAnterior = Paths.get("images").resolve(nombreFotoAnterior).toAbsolutePath();
            File archivoFotoAnterior = rutaArchivoAnterior.toFile();
            if(archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()){
                archivoFotoAnterior.delete();
            }
        }
    }

    /**
     * Metodo para obtener una imagen
     * @param nombreFoto Nombre de la imagen
     * @return Imagen
     */
    @GetMapping("/uploads/img/{nombreFoto:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){
        Path rutaArchivo = Paths.get("images").resolve(nombreFoto).toAbsolutePath();
        Resource recurso = null;

        log.info(rutaArchivo.toString());
        try {
            recurso = new UrlResource(rutaArchivo.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if(!recurso.exists()&& !recurso.isReadable()){
            throw new RuntimeException("No se pudo cargar la imagen: "+ nombreFoto);
        }

        HttpHeaders cabecera = new HttpHeaders();
        cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= \""+recurso.getFilename()+"\"");
        return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
    }
}