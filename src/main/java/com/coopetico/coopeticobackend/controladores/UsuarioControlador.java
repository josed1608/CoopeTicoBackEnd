package com.coopetico.coopeticobackend.controladores;
// Programador: Hannia Aguilar Salas
// Fecha: 11/04/2019
// Version: 2.0
// Controlador de usuarios, hice los métodos de actualizarContrasena: Cambiar la contraseña en la base de datos
// y mostrarInterfazCambioContrasena: Validar el token que se envío al mail del usuario para el cambio de contraseña.

import com.coopetico.coopeticobackend.entidades.GrupoEntidad;
import com.coopetico.coopeticobackend.entidades.TokenRecuperacionContrasenaEntidad;
import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.entidades.UsuarioTemporal;
import com.coopetico.coopeticobackend.mail.EmailServiceImpl;
import com.coopetico.coopeticobackend.repositorios.UsuariosRepositorio;
import com.coopetico.coopeticobackend.servicios.TokensRecuperacionContrasenaServicio;
import com.coopetico.coopeticobackend.servicios.TokensRecuperacionContrasenaServicioImpl;
import com.coopetico.coopeticobackend.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;

@CrossOrigin( origins = {"http://localhost:4200"})
@RestController
@RequestMapping(path="/usuarios")
public class UsuarioControlador {

    private TokensRecuperacionContrasenaServicioImpl tokensServicio;
    private EmailServiceImpl mail ;
    private UsuariosRepositorio usuariosRepositorio;
    private PasswordEncoder encoder;
    private TokensRecuperacionContrasenaServicio tokensRecuperacionContrasenaServicio;
    private UsuarioServicio usuarioServicio;
    private UsuarioTemporal usuarioTemporal;

    @Autowired
    public UsuarioControlador(UsuariosRepositorio usuariosRepositorio, PasswordEncoder encoder, TokensRecuperacionContrasenaServicio tokensRecuperacionContrasenaServicio, UsuarioServicio servicio) {
        this.usuarioServicio = servicio;
        this.usuariosRepositorio = usuariosRepositorio;
        this.encoder = encoder;
        this.tokensRecuperacionContrasenaServicio = tokensRecuperacionContrasenaServicio;
        this.usuarioTemporal = new UsuarioTemporal();
    }

    /**
     * Método que crea un nuevo token de cambio de contraseña para el usuario, lo ingresa en la base de datos y envía el email.
     * @param correo Correo del usuario
     * @return El JWT en caso de un éxito o fallo.
     */
    @GetMapping(path="/contrasenaToken")
    public @ResponseBody ResponseEntity recuperarContrasena (@RequestParam("correo") String correo) {
        String token = tokensServicio.insertarToken(correo);
        if (token == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        mail.sendSimpleMessage(correo, "Codigo de reseteo", token);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     *  Método que recibe el id (correo) y la nueva contraseña
     * @param datosUsuario Modelo del request de autenticación. Espera los atributos username y password
     * @return El JWT en caso de un cambio exitoso o fallido.
     */
    @PutMapping(path = "/recuperarContrasena")
    public ResponseEntity actualizarContrasena(@RequestBody AuthenticationRequest datosUsuario){
        //Obtener el usuario con ese nombre o correo
        String nombreUsuario = datosUsuario.getUsername();
        UsuarioEntidad usuarioEntidad = this.usuariosRepositorio.findById(nombreUsuario).orElseThrow(null);

        if(usuarioEntidad != null){
            //Encriptar la contraseña
            usuarioEntidad.setContrasena(encoder.encode(datosUsuario.getPassword()));
            //Actualizar el usuario
            usuariosRepositorio.save(usuarioEntidad);
            // Se borra de la tabla el Token
            tokensRecuperacionContrasenaServicio.eliminarToken(nombreUsuario);
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    ///// BEGIN -- PRUEBA DE VALIDACION DE TOKEN
      @GetMapping(path="/recuperarContrasenaEnProceso")
      public @ResponseBody String linkRecuperarContrasena (@RequestParam("id") String id) {
          return "MOSTRAR INTERFAZ DE RECUPERACIÓN DE CONTRASEÑA";
      }
      @GetMapping(path="/recuperarContrasenaFallido")
      public @ResponseBody String linkRecuperarContrasena2 () {
          return "NO MOSTRAR INTERFAZ DE RECUPERACIÓN DE CONTRASEÑA";
      }
     ///// END -- PRUEBA DE VALIDACION DE TOKEN

    /**
     * Método que revisa el token de cambio de contraseña con el que ingresa el usuario (link enviado al correo)
     * Valida el token y lo redirecciona a realizar el cambio o a un error en el link.
     * @param id Correo del usuario
     * @param token Token del link enviado al correo, generado para el cambio de contraseña
     * @return Retorna la dirección a la que se dirije al usuario.
     */
    @RequestMapping(value = "/recuperarContrasena", method = RequestMethod.GET)
    public String mostrarInterfazCambioContrasena( @RequestParam("id") String id, @RequestParam("token") String token) {
        System.out.println("Intenta entrar a recuperar contrasena");
        boolean resultadoValidacion = validarTokenRecuperarContrasena(id);
        if (!resultadoValidacion) {
            return "redirect:/usuarios/recuperarContrasenaFallido";
        }
        return "redirect:/usuarios/recuperarContrasenaEnProceso?id=" + id;
    }




    //TODO ver lo de acoplamiento y cohesion
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioTemporal crearUsuario(@RequestBody UsuarioTemporal usuario){
        UsuarioEntidad temp = usuarioServicio.agregarUsuario(usuario.convertirAUsuarioEntidad(), usuario.getIdGrupo());
        return new UsuarioTemporal(temp);
    }

    @GetMapping()
    public List<UsuarioTemporal> obtenerUsuarios(){
        List<UsuarioEntidad> usuarios = usuarioServicio.obtenerUsuarios();
        return usuarioTemporal.getListaUsuarioTemporal(usuarios);
    }


    @GetMapping("/{id}")
    public UsuarioTemporal obtenerUsuarioPorId(@PathVariable String id){
        UsuarioEntidad usuarioEntidad = null;
        if( usuarioServicio.usuarioPorCorreo(id).isPresent() )
            usuarioEntidad = usuarioServicio.usuarioPorCorreo(id).get();
        return new UsuarioTemporal(usuarioEntidad);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarUsuarioPorId(@PathVariable String id){
        usuarioServicio.eliminar(id);
    }


    @GetMapping("/grupo")
    public List<UsuarioTemporal> obtenerUsuariosPorGrupo(@RequestBody GrupoEntidad grupoEntidad){
        List<UsuarioEntidad> usuarios = usuarioServicio.obtenerUsuariosPorGrupo(grupoEntidad);
        return usuarioTemporal.getListaUsuarioTemporal(usuarios);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioTemporal actualizar(@RequestBody UsuarioTemporal usuario, @PathVariable String id){
        UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
        if(this.usuarioServicio.usuarioPorCorreo(id).isPresent())
            usuarioEntidad = this.usuarioServicio.usuarioPorCorreo(id).get();
        usuarioEntidad.setApellidos(usuario.getApellidos());
        usuarioEntidad.setNombre(usuario.getNombre());
        usuarioEntidad.setTelefono(usuario.getTelefono());
        usuarioEntidad.setFoto(usuario.getFoto());

        UsuarioEntidad temporal = usuarioServicio.agregarUsuario(usuarioEntidad, usuarioEntidad.getGrupoId());
        return new UsuarioTemporal(temporal);
    }

    /**
     * Método para validar el token, revisa que exista un token para ese usuario (correo) y que la fecha del link no haya expirado.
     * @param id Correo del usuario
     * @return Boolean que indica si es válido o no.
     */
    private boolean validarTokenRecuperarContrasena(String id) {
        TokenRecuperacionContrasenaEntidad tokenContrasena  = tokensRecuperacionContrasenaServicio.getToken(id);
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


}