package com.coopetico.coopeticobackend.controladores;

import com.coopetico.coopeticobackend.entidades.bd.TaxistaEntidad;
import com.coopetico.coopeticobackend.entidades.TaxistaEntidadTemporal;
import com.coopetico.coopeticobackend.excepciones.UsuarioNoEncontradoExcepcion;
import com.coopetico.coopeticobackend.mail.EmailServiceImpl;
import com.coopetico.coopeticobackend.servicios.TaxistasServicio;
import com.coopetico.coopeticobackend.servicios.TokensRecuperacionContrasenaServicioImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 Controlador de la entidad Taxista para consultar, insertar, modificar y eliminar taxistas.
 @author      Christofer Rodriguez Sanchez.
 @since       16-04-2019.
 @version    1.0.
 */

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/taxistas")
public class TaxistasControlador {

    /**
     * Servicio de taxistas para consultar datos.
     */
    @Autowired
    private TaxistasServicio taxistaServicio;

    /**
     * Servicio generador de token para establecer la contrasenna.
     */
    @Autowired
    private TokensRecuperacionContrasenaServicioImpl tokensServicio;

    /**
     * Servicio para enviar correos.
     */
    @Autowired
    private EmailServiceImpl correoServicio;

    /**
     * Logger para subir la imagen
     */
    private final Logger log = LoggerFactory.getLogger(TaxistasControlador.class);

    /**
     * Utilidades para subir y eliminar imagenes, para no duplicar tanto código
     */
    private UtilidadesControlador utilidadesControlador;

    /**
     * Constructor
     */
    private TaxistasControlador(){
        this.utilidadesControlador = new UtilidadesControlador();
    }

    /**
     * Funcion que obtiene los taxistas existentes en el sistema.
     * @return Lista de taxistas, correo, nombre, apellidos, telefono y estado.
     */
    @GetMapping("/taxistas")
    public List<TaxistaEntidadTemporal> consultar() {
        return taxistaServicio.consultar();
    }

    /**
     * Funcion que obtiene un taxista.
     * @param correoUsuario correo del taxista.
     * @return Lista de taxistas, correo, nombre, apellidos, telefono y estado.
     */
    @GetMapping("/taxistas/{correoUsuario}")
    public TaxistaEntidadTemporal consultarPorId(@PathVariable String correoUsuario) {
        return taxistaServicio.consultarPorId(correoUsuario);
    }

    /**
     * Funcion que agrega un taxista.
     * @param taxista Taxista que se desea agregar.
     * @return Taxista agregado.
     */
    @PostMapping("/taxistas")
    @ResponseStatus(HttpStatus.CREATED)
    public TaxistaEntidadTemporal agregar(@RequestBody TaxistaEntidadTemporal taxista){
        TaxistaEntidadTemporal taxistaCreado = taxistaServicio.guardar(taxista, taxista.getPkCorreoUsuario());
        if (taxistaCreado != null) {
            String token = tokensServicio.insertarToken(taxistaCreado.getPkCorreoUsuario());
            if (token != null) {
                this.correoServicio.enviarCorreoRegistro(taxistaCreado.getPkCorreoUsuario(), token);
            }
        }
        return taxistaCreado;
    }

    /**
     * Funcion que modifica un taxista.
     * @param taxista Taxista que se desea modificar.
     * @param pkCorreoUsuario Correo del taxista que se desea modificar.
     * @return Taxista modificado.
     */
    @PutMapping("/taxistas/{pkCorreoUsuario}")
    @ResponseStatus(HttpStatus.CREATED)
    public TaxistaEntidadTemporal modificar(@RequestBody TaxistaEntidadTemporal taxista, @PathVariable("pkCorreoUsuario") String pkCorreoUsuario){
        return taxistaServicio.guardar(taxista, pkCorreoUsuario);
    }

    /**
     * Metodo que elimina un taxista.
     * @param correoUsuario Correo del taxista que se desea eliminar.
     */
    @DeleteMapping("/taxistas/{correoUsuario}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable String correoUsuario){
        taxistaServicio.eliminar(correoUsuario);
    }


    /**
     * Metodo para subir imagen
     * @param archivo Archivo a subir
     * @param id Identificador del taxista
     * @return Respuesta correcto o incorrecto y el taxista con la foto agregada
     */
    @PostMapping("/taxistas/upload")
    public ResponseEntity<?> subirImagen( @RequestParam("archivo") MultipartFile archivo, @RequestParam("id") String id){
        Map<String, Object> response = new HashMap<>();

        Optional<TaxistaEntidad> optionalTaxistaEntidad = taxistaServicio.taxistaPorCorreo(id);
        TaxistaEntidad taxistaEntidad;
        taxistaEntidad = optionalTaxistaEntidad.orElse(null);
        if( taxistaEntidad == null) {
            response.put("mensaje", "Error: no se pudo editar, el taxista ID: ".concat(id.concat(" no existe en la base de datos")));
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

            this.utilidadesControlador.eliminarFoto(taxistaEntidad.getUsuarioByPkCorreoUsuario().getFoto());

            taxistaEntidad.getUsuarioByPkCorreoUsuario().setFoto(nombreArchivo);
            TaxistaEntidadTemporal taxistaEntidadTemporal = new TaxistaEntidadTemporal(taxistaEntidad);
            this.modificar(taxistaEntidadTemporal, taxistaEntidad.getPkCorreoUsuario());
            response.put("taxista",taxistaEntidad);
            response.put("mensaje", "Has subido correctamente la imagen "+nombreArchivo);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Devuelve el estado y justificacion del taxista
     * @param correo Correo del taxista
     * @return Estado y justificacion del taxista
     * @author Kevin Jiménez
     */
    @GetMapping("{correo}/estado")
    public ResponseEntity obtenerEstado(@PathVariable String correo){
        try{
            return new ResponseEntity(taxistaServicio.obtenerEstado(correo), HttpStatus.OK);
        }catch (UsuarioNoEncontradoExcepcion e){
            return new ResponseEntity("{\"error\" : \""+e.getMessage()+"\"}", HttpStatus.NOT_FOUND);
        } catch (Exception general){
            return new ResponseEntity("{\"error\" : \"Ha ocurrido un error interno.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}