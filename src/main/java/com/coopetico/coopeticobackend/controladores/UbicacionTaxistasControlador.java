package com.coopetico.coopeticobackend.controladores;

import com.coopetico.coopeticobackend.entidades.UbicacionTaxistaEntidad;
import com.coopetico.coopeticobackend.excepciones.UbicacionNoEncontradaExcepcion;
import com.coopetico.coopeticobackend.excepciones.UsuarioNoEncontradoExcepcion;
import com.coopetico.coopeticobackend.servicios.TaxistasServicio;
import com.coopetico.coopeticobackend.servicios.UbicacionTaxistasServicio;
import com.google.maps.model.LatLng;
import org.springframework.data.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

/**
 Controlador para los requests relacionados con ubicaciones de taxistas.
 @author      Marco Venegas
 @since       13-05-2019
 @version     1.0
 */
@RestController
@RequestMapping(path = "/ubicaciones")
public class UbicacionTaxistasControlador {

    private final
    UbicacionTaxistasServicio ubicacionTaxistasServicio;

    private final
    TaxistasServicio taxistasServicio;

    @Autowired
    public UbicacionTaxistasControlador(UbicacionTaxistasServicio ubicacionTaxistasServicio, TaxistasServicio taxistasServicio){
        this.ubicacionTaxistasServicio = ubicacionTaxistasServicio;
        this.taxistasServicio = taxistasServicio;
    }


    /**
     * Endpoint para insertar o actualizar la ubicacion de un taxista
     *
     * @param ubicacionBody Modelo de una ubicación.
     *                      Espera los atributos correoTaxista, latitud y longitud.
     * @return Mensaje de confirmación
     */
    @PostMapping("/actualizar")
    public ResponseEntity actualizar(@RequestBody UbicacionTaxistaEntidad ubicacionBody) {
        String correoTaxista = ubicacionBody.getCorreoTaxista();
        //Reviso que si exista el taxista para no poder ingresar en la estructura correos no registrados.
        taxistasServicio.taxistaPorCorreo(correoTaxista).orElseThrow(() -> new UsuarioNoEncontradoExcepcion("Taxista " + correoTaxista + " no encontrado", HttpStatus.NOT_FOUND, System.currentTimeMillis()));

        double latitud = ubicacionBody.getLatitud();
        double longitud = ubicacionBody.getLongitud();

        Pair<String, LatLng> ubicacion = Pair.of(correoTaxista, new LatLng(latitud, longitud));

        ubicacionTaxistasServicio.upsertTaxista(ubicacion);

        return ok("Ubicación actualizada");
    }

    /**
     * Endpoint para consultar la ubicacion de un taxista
     *
     * @param correoTaxista El correo del taxista del cuál se quiere conocer la ubicación.
     *
     * @return Modelo de una ubicación con los atributos correoTaxista, latitud y longitud.
     */
    @GetMapping(path = "/consultar/{correoTaxista}")
    public ResponseEntity consultar(@PathVariable String correoTaxista){
        //Reviso que me pidan un taxista existente.
        taxistasServicio.taxistaPorCorreo(correoTaxista).orElseThrow(() -> new UsuarioNoEncontradoExcepcion("Taxista " + correoTaxista + " no encontrado", HttpStatus.NOT_FOUND, System.currentTimeMillis()));
        Pair<Double, Double> ubicacion = ubicacionTaxistasServicio.consultarUbicacionPair(correoTaxista);
        if(ubicacion == null){
            throw new UbicacionNoEncontradaExcepcion("Ubicacion no registrada", HttpStatus.NOT_FOUND, System.currentTimeMillis());
        }
        double latitud = ubicacion.getFirst();
        double longitud = ubicacion.getSecond();

        UbicacionTaxistaEntidad ubicacionEntidad = new UbicacionTaxistaEntidad(correoTaxista, latitud, longitud);

        return ok(ubicacionEntidad);
    }

    /**
     * Endpoint para eliminar la ubicacion de un taxista
     *
     * @param correoTaxista El correo del taxista del cuál se quiere eliminar la ubicación.
     *
     * @return Mensaje de confirmación
     */
    @DeleteMapping(path = "/eliminar/{correoTaxista}")
    public ResponseEntity eliminar(@PathVariable String correoTaxista){
        //Reviso que me pidan un taxista existente.
        taxistasServicio.taxistaPorCorreo(correoTaxista).orElseThrow(() -> new UsuarioNoEncontradoExcepcion("Taxista " + correoTaxista + " no encontrado", HttpStatus.NOT_FOUND, System.currentTimeMillis()));

        ubicacionTaxistasServicio.eliminarTaxista(correoTaxista);

        return ok("Ubicación eliminada.");
    }

}
