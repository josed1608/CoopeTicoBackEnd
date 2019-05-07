package com.coopetico.coopeticobackend.controladores;

import com.coopetico.coopeticobackend.entidades.TaxiEntidad;
import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.entidades.UsuarioTemporal;
import com.coopetico.coopeticobackend.servicios.TaxisServicio;
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
 * Controlador de taxis
 * @autor   Jorge Araya González
 */

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/taxis")
public class TaxisControlador {

    /**
     * Servicio de taxis
     */
    @Autowired
    private TaxisServicio taxisServicio;

    /**
     * Método para consultar todos los taxis de la base de datos
     * @return lista de entidades de taxi
     */
    @GetMapping("/taxis")
    public List<TaxiEntidad> consultar(){
        return taxisServicio.consultar();
    }

    /**
     * Método para consultar un taxi especifico
     * @param id placa del taxi a consultar
     * @return Entidad taxi que coincide con el id pasado por parámetro
     */
    @GetMapping("/taxis/{id}")
    public TaxiEntidad consultarPorId(@PathVariable String id){
        return taxisServicio.consultarPorId(id);
    }

    private final Logger log = LoggerFactory.getLogger(UsuarioControlador.class);

    /**
     * Método para guardar un taxi nuevo en la base de datos
     * @param taxi entidad taxi que se quiere agregar
     * @return El taxi que se agregó a la base de datos
     */
    @PostMapping("/taxis")
    @ResponseStatus(HttpStatus.CREATED)
    public TaxiEntidad agregar(@RequestBody TaxiEntidad taxi){

        return taxisServicio.guardar(taxi);
    }

    /**
     * Método para modificar un taxi existente en la base de datos
     * @param taxi Entidad taxi modificada
     * @param id Placa del taxi a modificar
     * @return Entidad del taxi modificado
     */
    @PutMapping("/taxis/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public TaxiEntidad modificar(@RequestBody TaxiEntidad taxi, @PathVariable String id){
        TaxiEntidad taxiActual = taxisServicio.consultarPorId(id);
        taxiActual.setPkPlaca(taxi.getPkPlaca());
        taxiActual.setClase(taxi.getClase());
        taxiActual.setDatafono(taxi.getDatafono());
        taxiActual.setFechaVenMarchamo(taxi.getFechaVenMarchamo());
        taxiActual.setFechaVenSeguro(taxi.getFechaVenSeguro());
        taxiActual.setFechaVenRtv(taxi.getFechaVenRtv());
        taxiActual.setTelefono(taxi.getTelefono());
        taxiActual.setTipo(taxi.getTipo());

        return taxisServicio.guardar(taxiActual);
    }

    /**
     * Método para eliminar un taxi de la base de datos
     * @param id Placa del taxi a eliminar
     */
    @DeleteMapping("/taxis/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable String id){
        taxisServicio.eliminar(id);
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
//
//        Optional<UsuarioEntidad> optionalUsuarioEntidad = usuarioServicio.usuarioPorCorreo(id);
//        UsuarioEntidad usuarioEntidad = null;
//        usuarioEntidad = optionalUsuarioEntidad.orElse(null);
//        if( usuarioEntidad == null) {
//            response.put("mensaje", "Error: no se pudo editar, el usuario ID: ".concat(id.concat(" no existe en la base de datos")));
//            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
//        }
//
//        if( !archivo.isEmpty()){
//            String nombreArchivo = UUID.randomUUID().toString()+"_" + archivo.getOriginalFilename().replace(" ","");
//            Path rutaArchivo = Paths.get("images").resolve(nombreArchivo).toAbsolutePath();
//            log.info(rutaArchivo.toString());
//
//            try {
//                Files.copy(archivo.getInputStream(), rutaArchivo);
//            } catch (IOException e) {
//                response.put("mensaje", "Error al subir la imagen del usuario");
//                response.put("error", e.getMessage().concat(":").concat(e.getCause().getMessage()));
//                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//
//            this.eliminarFoto(usuarioEntidad.getFoto());
//
//
//            usuarioEntidad.setFoto(nombreArchivo);
//            UsuarioTemporal usuarioTemporal = new UsuarioTemporal(usuarioServicio.crearUsuario(usuarioEntidad));
//            response.put("usuario",usuarioTemporal);
//            response.put("mensaje", "Has subido correctamente la imagen "+nombreArchivo);
//        }
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
