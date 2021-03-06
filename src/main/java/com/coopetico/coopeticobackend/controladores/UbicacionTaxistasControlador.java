package com.coopetico.coopeticobackend.controladores;

import com.coopetico.coopeticobackend.entidades.UbicacionTaxistaEntidad;
import com.coopetico.coopeticobackend.entidades.bd.TaxiEntidad;
import com.coopetico.coopeticobackend.entidades.bd.TaxistaEntidad;
import com.coopetico.coopeticobackend.excepciones.UsuarioNoEncontradoExcepcion;
import com.coopetico.coopeticobackend.servicios.TaxisServicio;
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
@CrossOrigin
@RestController
@RequestMapping(path = "/ubicaciones")
public class UbicacionTaxistasControlador {

    private final
    UbicacionTaxistasServicio ubicacionTaxistasServicio;

    private final
    TaxistasServicio taxistasServicio;

    private final TaxisServicio taxisServicio;

    @Autowired
    public UbicacionTaxistasControlador(UbicacionTaxistasServicio ubicacionTaxistasServicio, TaxistasServicio taxistasServicio, TaxisServicio taxisServicio){
        this.ubicacionTaxistasServicio = ubicacionTaxistasServicio;
        this.taxistasServicio = taxistasServicio;
        this.taxisServicio = taxisServicio;
    }


    /**
     * Endpoint para insertar o actualizar la ubicacion y el estado de disponibilidad de un taxista.
     *
     * @param ubicacionBody Modelo de una ubicación.
     *                      Espera los atributos correoTaxista, latitud, longitud y disponible.
     * @return Mensaje de confirmación
     */
    @PostMapping("/actualizar/todo")
    public ResponseEntity actualizarUbicacionDisponible(@RequestBody UbicacionTaxistaEntidad ubicacionBody) {

        String correoTaxista = ubicacionBody.getCorreoTaxista();
        //Reviso que si exista el taxista para no poder ingresar en la estructura correos no registrados.
        taxistasServicio.taxistaPorCorreo(correoTaxista).orElseThrow(() -> new UsuarioNoEncontradoExcepcion("Taxista " + correoTaxista + " no encontrado", HttpStatus.NOT_FOUND, System.currentTimeMillis()));

        double latitud = ubicacionBody.getLatitud();
        double longitud = ubicacionBody.getLongitud();
        boolean disponible = ubicacionBody.getDisponible();

        ubicacionTaxistasServicio.upsertUbicacionDisponibleTaxista(correoTaxista, new LatLng(latitud, longitud), disponible);

        return ok("Ubicación y estado de disponibilidad actualizados");
    }

    /**
     * Endpoint para insertar o actualizar solo la ubicación del taxista
     *
     * @param ubicacionBody Modelo de una ubicación.
     *                      Espera los atributos correoTaxista, latitud y longitud.
     * @return Mensaje de confirmación
     */
    @PostMapping("/actualizar/ubicacion")
    public ResponseEntity actualizarUbicacion(@RequestBody UbicacionTaxistaEntidad ubicacionBody) {

        String correoTaxista = ubicacionBody.getCorreoTaxista();
        //Reviso que si exista el taxista para no poder ingresar en la estructura correos no registrados.
        taxistasServicio.taxistaPorCorreo(correoTaxista).orElseThrow(() -> new UsuarioNoEncontradoExcepcion("Taxista " + correoTaxista + " no encontrado", HttpStatus.NOT_FOUND, System.currentTimeMillis()));

        double latitud = ubicacionBody.getLatitud();
        double longitud = ubicacionBody.getLongitud();

        ubicacionTaxistasServicio.upsertUbicacionTaxista(correoTaxista, new LatLng(latitud, longitud));

        return ok("Ubicación actualizada");
    }

    /**
     * Endpoint para insertar o actualizar solo el estado de disponibilidad del taxista
     *
     * @param ubicacionBody Modelo de una ubicación.
     *                      Espera los atributos correoTaxista y disponible.
     * @return Mensaje de confirmación
     */
    @PostMapping("/actualizar/disponible")
    public ResponseEntity actualizarDisponible(@RequestBody UbicacionTaxistaEntidad ubicacionBody) {

        String correoTaxista = ubicacionBody.getCorreoTaxista();
        //Reviso que si exista el taxista para no poder ingresar en la estructura correos no registrados.
        taxistasServicio.taxistaPorCorreo(correoTaxista).orElseThrow(() -> new UsuarioNoEncontradoExcepcion("Taxista " + correoTaxista + " no encontrado", HttpStatus.NOT_FOUND, System.currentTimeMillis()));

        boolean disponible = ubicacionBody.getDisponible();

        ubicacionTaxistasServicio.updateDisponibleTaxista(correoTaxista, disponible);

        return ok("Estado de disponibilidad actualizado");
    }

    /**
     * Endpoint para consultar la ubicacion y el estado de disponibilidad de un taxista.
     *
     * @param correoTaxista El correo del taxista del cuál se quiere conocer la ubicación y el estado de disponibilidad.
     *
     * @return Modelo de una ubicación con los atributos correoTaxista, latitud y longitud, y disponible.
     */
    @GetMapping(path = "/consultar/{correoTaxista}")
    public ResponseEntity consultarUbicacionDisponible(@PathVariable String correoTaxista){

        Object[] datos = ubicacionTaxistasServicio.consultarUbicacionPairDisponible(correoTaxista);

        Pair<Double, Double> ubicacion = (Pair<Double, Double>)datos[0];

        double latitud = ubicacion.getFirst();
        double longitud = ubicacion.getSecond();
        boolean disponible = (boolean)datos[1];

        UbicacionTaxistaEntidad ubicacionEntidad = new UbicacionTaxistaEntidad(correoTaxista, latitud, longitud, disponible);

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

    /**
     * Inserta datos de prueba en las estructura de datos de taxistas
     * @return ok indicando que se insertaron
     */
    @PostMapping("/cargar-datos-test")
    public ResponseEntity cargarTaxistasDePrueba(){
        String[] taxistas = {"taxista1@taxista.com", "taxista2@taxista.com"};
        // Lincoln Plaza, Centro Comercial Guadalupe, Facultad de Derecho, Parque Morazán, Gorilla Logic, Parque de la Paz
        LatLng[] ubicaciones = {new LatLng(9.963111, -84.054929), new LatLng(9.942293, -84.064481)};
        boolean[] disponibles = {true, true};

        TaxistaEntidad taxista  = taxistasServicio.taxistaPorCorreo("taxista1@taxista.com").orElse(null);
        TaxiEntidad taxi = taxisServicio.consultarPorId("AAA111");
        taxistasServicio.actualizarTaxiActual(taxista, taxi);
        taxista  = taxistasServicio.taxistaPorCorreo("taxista2@taxista.com").orElse(null);
        taxi = taxisServicio.consultarPorId("BBB111");
        taxistasServicio.actualizarTaxiActual(taxista, taxi);

        for(int i = 0; i < 2; i++) {
            this.ubicacionTaxistasServicio.upsertUbicacionDisponibleTaxista(taxistas[i], ubicaciones[i], disponibles[i]);
        }
        return ok("Taxis de test agregados");
    }
}
