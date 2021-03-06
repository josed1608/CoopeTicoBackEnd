package com.coopetico.coopeticobackend.controladores;

import com.coopetico.coopeticobackend.entidades.bd.TaxiEntidad;
import com.coopetico.coopeticobackend.servicios.TaxisServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Controlador de taxis
 * @autor   Jorge Araya González
 */

@CrossOrigin
@RestController
@RequestMapping("/taxis")
public class TaxisControlador {

    /**
     * Servicio de taxis
     */
    @Autowired
    private TaxisServicio taxisServicio;

    /**
     * Logger para subir la imagen
     */
    private final Logger log = LoggerFactory.getLogger(UsuarioControlador.class);


    /**
     * Utilidades para subir y eliminar imagenes, para no duplicar tanto código
     */
    private UtilidadesControlador utilidadesControlador;

    /**
     * Constructor del controlador de taxis
     */
    public TaxisControlador(){
        this.utilidadesControlador = new UtilidadesControlador();
    }

    /**
     * Método para consultar todos los taxis válidos de la base de datos
     * @return lista de entidades de taxi
     */
    @GetMapping("/taxis")
    public List<TaxiEntidad> consultar(){
        /*
        List<TaxiEntidad> taxisValidos = new ArrayList<>();
        List<TaxiEntidad> taxis = taxisServicio.consultar();

        for (TaxiEntidad taxi : taxis){
            if(taxi.getValido() == true){
                taxisValidos.add(taxi);
            }
        }

         */

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
        taxiActual.setValido(taxi.getValido());
        taxiActual.setEstado(taxi.isEstado());
        taxiActual.setJustificacion(taxi.getJustificacion());
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
     * @param id Identificador del taxi
     * @return Respuesta correcto o incorrecto y el taxi con la foto agregada
     */
    @PostMapping("/taxis/upload")
    public ResponseEntity<?> subirImagen(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") String id){
        Map<String, Object> response = new HashMap<>();

        TaxiEntidad taxiEntidad = taxisServicio.consultarPorId(id);
        if( taxiEntidad == null) {
            response.put("mensaje", "Error: no se pudo editar, el taxi ID: ".concat(id.concat(" no existe en la base de datos")));
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

            this.utilidadesControlador.eliminarFoto(taxiEntidad.getFoto());

            taxiEntidad.setFoto(nombreArchivo);
            this.modificar(taxiEntidad, taxiEntidad.getPkPlaca());
            response.put("taxi",taxiEntidad);
            response.put("mensaje", "Has subido correctamente la imagen "+nombreArchivo);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Metodo para guardar una lista de taxis
     * @param taxis lista de los taxis a guardar
     * @return ok si la insercion fue exitosa
     */
    @PostMapping()
    public ResponseEntity guardarTaxisArchivo(@RequestBody List<TaxiEntidad> taxis) {
        this.taxisServicio.guardarLista(taxis);
        return ok("");
    }
}
