package com.coopetico.coopeticobackend.controladores;

import com.coopetico.coopeticobackend.entidades.DatosTaxistaAsigadoEntidad;
import com.coopetico.coopeticobackend.entidades.bd.TaxistaEntidad;
import com.coopetico.coopeticobackend.entidades.TaxistaEntidadTemporal;
import com.coopetico.coopeticobackend.entidades.bd.UsuarioEntidad;
import com.coopetico.coopeticobackend.entidades.UsuarioTemporal;
import com.coopetico.coopeticobackend.excepciones.UsuarioNoEncontradoExcepcion;
import com.coopetico.coopeticobackend.mail.EmailServiceImpl;
import com.coopetico.coopeticobackend.servicios.TaxistasServicio;
import com.coopetico.coopeticobackend.servicios.TokensRecuperacionContrasenaServicioImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
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


    private final Logger log = LoggerFactory.getLogger(UsuarioControlador.class);

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
     * @param id Identificador del usuario
     * @return Respuesta correcto o incorrecto y el usuario con la foto agregada
     */
    @PostMapping("/upload")
    public ResponseEntity<?> subirImagen(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") String id){
        Map<String, Object> response = new HashMap<>();

        Optional<TaxistaEntidad> optionalTaxistaEntidad = taxistaServicio.taxistaPorCorreo(id);
        TaxistaEntidad taxistaEntidad = null;
        taxistaEntidad = optionalTaxistaEntidad.orElse(null);
        if( taxistaEntidad == null) {
            response.put("mensaje", "Error: no se pudo editar, el taxista ID: ".concat(id.concat(" no existe en la base de datos")));
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

            this.eliminarFoto(taxistaEntidad.getUsuarioByPkCorreoUsuario().getFoto());


            taxistaEntidad.getUsuarioByPkCorreoUsuario().setFoto(nombreArchivo);
            // UsuarioTemporal usuarioTemporal = new UsuarioTemporal(taxistaServicio.guardar(taxistaEntidad));
            // response.put("usuario",usuarioTemporal);
            // response.put("mensaje", "Has subido correctamente la imagen "+nombreArchivo);
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

    //-------------------------------------------------------------------------
    /**
     * Trae los datos a ser desplegados del taxista asignado.
     *
     * @author Joseph Rementería (b55824)
     * @since 15-05-2019
     *
     * @param correoTaxista el correo del taxista asignado
     * @param json un JSON con el correo del cliente, el origen y el destino
     * @return los datos a desplegar del taxista
     */
    @GetMapping("{correoTaxista}/datosParaMostrar")
    public DatosTaxistaAsigadoEntidad obtenerDatosTaxistaAsignado(
        @PathVariable String correoTaxista,
        @RequestBody Map<String, String> json
    ){
        //---------------------------------------------------------------------
        // Se llama el servicio que trae los datos de la base
        DatosTaxistaAsigadoEntidad resultado =
            this.taxistaServicio.obtenerDatosTaxistaAsignado(correoTaxista);
        //---------------------------------------------------------------------
        // Se le anexan a los datos los que han ido saltando
        // de endpoint en endpoint
        resultado.setCorreoCliente(json.get("correoCliente"));
        resultado.setOrigen(json.get("origen"));
        resultado.setDestino(json.get("destino"));
        //---------------------------------------------------------------------
        return resultado;
        //---------------------------------------------------------------------
    }
    //-------------------------------------------------------------------------
}