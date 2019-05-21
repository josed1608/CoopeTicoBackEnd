//-----------------------------------------------------------------------------
package com.coopetico.coopeticobackend.controladores;
//-----------------------------------------------------------------------------
import com.coopetico.coopeticobackend.entidades.DatosTaxistaAsigadoEntidad;
import com.coopetico.coopeticobackend.entidades.ViajeComenzandoEntidad;
import com.coopetico.coopeticobackend.entidades.ViajeEntidadTemporal;
import com.coopetico.coopeticobackend.entidades.bd.UsuarioEntidad;
import com.coopetico.coopeticobackend.excepciones.UsuarioNoEncontradoExcepcion;
import com.coopetico.coopeticobackend.servicios.*;
import com.coopetico.coopeticobackend.entidades.ViajeTmpEntidad;

import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;
//-----------------------------------------------------------------------------
@CrossOrigin( origins = {"http://localhost:4200"})
@RestController
@Validated
@Controller
@RequestMapping(path = "/viajes")
/**
 * Autor:
 * (1) Joseph Rementería (b55824).
 * <p>
 * Fecha: 06/04/2019.
 * <p>
 * Este es el controlador Viajes. Se encarga de la comunicación entre
 * la capa V y el M viajes.
 **/
public class ViajeControlador {
    //-------------------------------------------------------------------------
    // Variables globales.
    private ViajesServicio viajesServicio;
    private final UsuarioServicio usuarioServicio;
    private final TaxistasServicio taxistasServicio;
    private final UbicacionTaxistasServicio ubicacionTaxistasServicio;
    private final DistanciaServicio distanciaServicio;
    private final SimpMessagingTemplate template;
    //-------------------------------------------------------------------------
    // Métodos.
    /**
     * Autor: Joseph Rementería (b55824).
     * Fecha: 06/04/2019.
     *
     * Constructor de la clase.
     * @param vjsRep repositorio de viajes ya creado
     * @param taxistasServicio
     * @param ubicacionTaxistasServicio
     * @param distanciaServicio
     *
     */
    @Autowired
    public ViajeControlador(ViajesServicio vjsRep, UsuarioServicio usuarioServicio, TaxistasServicio taxistasServicio, UbicacionTaxistasServicio ubicacionTaxistasServicio, DistanciaServicio distanciaServicio, SimpMessagingTemplate template) {
        this.viajesServicio = vjsRep;
        this.usuarioServicio = usuarioServicio;
        this.taxistasServicio = taxistasServicio;
        this.ubicacionTaxistasServicio = ubicacionTaxistasServicio;
        this.distanciaServicio = distanciaServicio;
        this.template = template;
    }

    /**
     * Autor: Joseph Rementería (b55824).
     * Fecha: 06/04/2019.
     *
     * Gaurda una tupla en la base de datos.
     *
     * @param viajeTempEntidad entidad dummy crada para recibir los datos 
     * del viaje del front end
     * @return String se inserto, null de otra manera.
     */
    @PostMapping()
    public ResponseEntity agregarViaje (@RequestBody ViajeTmpEntidad viajeTempEntidad) {
        try  {
            this.viajesServicio.guardar(
                viajeTempEntidad.getPlaca(),
                viajeTempEntidad.getCorreoCliente(),
                viajeTempEntidad.getFechaInicio(),
                viajeTempEntidad.getFechaFin(),
                viajeTempEntidad.getCosto(),
                viajeTempEntidad.getEstrellas(),
                viajeTempEntidad.getOrigenDestino(),
                viajeTempEntidad.getOrigenDestino(),
                viajeTempEntidad.getCorreoTaxista()
            );
            return ok("Viaje agregado");
        } catch (Exception e){
            return ok("error");
        }
    }


    /**
     * Metodo para obtener una lista de usuarios
     * @return Lista de usuarios
     */
    @GetMapping()
    public List<ViajeEntidadTemporal> obtenerViajes(){
        ViajeEntidadTemporal viajeEntidadTemporal = new ViajeEntidadTemporal();
        List<ViajeEntidadTemporal> listaTemporal = viajeEntidadTemporal.convertirListaViajes(viajesServicio.consultarViajes());
        for (ViajeEntidadTemporal viajeTemporal : listaTemporal) {
            viajeTemporal.setNombreCliente(this.obtenerNombreUsuario(viajeTemporal.getCorreoCliente()));
            viajeTemporal.setNombreTaxista(this.obtenerNombreUsuario(viajeTemporal.getCorreoTaxista()));
            viajeTemporal.setNombreOperador(this.obtenerNombreUsuario(viajeTemporal.getCorreoOperador()));
        }
        return listaTemporal;
    }

    /**
     * Método que permite obtener el nombre completo de un usuario.
     * @param correo Id Usuario
     * @return Nombre y apellidos
     */
    private String obtenerNombreUsuario(String correo){
        UsuarioEntidad usuarioTemporal = usuarioServicio.usuarioPorCorreo(correo).get();
        return usuarioTemporal.getNombre() + ' ' + usuarioTemporal.getApellido1() + ' ' + usuarioTemporal.getApellido2();
    }

    /**
     * Endpoint para que un cliente solicite un viaje. El cliente envía la información básica de un viaje, se escoge el taxista más cercano y se le avisa al taxista que espere
     * @param datosViaje Datos del viaje que desea hacer el cliente
     * @return retorna ok si se pudo escoger al primer taxista y una excepción si no
     */
    @GetMapping("/solicitar")
    public ResponseEntity solicitarViaje(@RequestBody ViajeComenzandoEntidad datosViaje) {
        List<Pair<String, LatLng>> taxistasDisponibles = ubicacionTaxistasServicio.obtenerTaxistasDisponibles(new LinkedList<>());

        LatLng origen = new LatLng(Double.parseDouble(datosViaje.getOrigen().split(",")[0]), Double.parseDouble(datosViaje.getOrigen().split(",")[1]));
        try {
            String taxistaEscogido = distanciaServicio.taxistaMasCercano(origen, taxistasDisponibles);

            this.template.convertAndSend("/user/" + taxistaEscogido + "/queue/recibir-viaje", datosViaje);

            return ok("Se le avisó al primer taxista " + taxistaEscogido);
        } catch (ApiException | InterruptedException | IOException e) {
            throw new UsuarioNoEncontradoExcepcion("No se logró encontrar taxista para el viaje", HttpStatus.NOT_FOUND, System.currentTimeMillis());
        }
    }

    /**
     * Endpoint para que un taxista acepte o rechace un viaje
     * @param respuesta respuesta del taxista
     * @param datosViaje datos del viaje que se acepta o rechaza
     * @param principal usuario taxista que acepta o rechaza
     * @return retorna la respuesta de lo que se logró
     */
    @PostMapping("/aceptar-rechazar")
    public ResponseEntity respuestaTaxista(@RequestParam boolean respuesta, @RequestBody ViajeComenzandoEntidad datosViaje, Principal principal) {
        if(respuesta) {
            DatosTaxistaAsigadoEntidad taxistaAsignado = taxistasServicio.obtenerDatosTaxistaAsignado(principal.getName());
            taxistaAsignado.setViaje(datosViaje);
            ubicacionTaxistasServicio.updateDisponibleTaxista(principal.getName(), false);

            template.convertAndSend("/user/" + datosViaje.getCorreoCliente() + "/queue/esperar-taxista", taxistaAsignado);

            return ok("Viaje comienza");
        }
        else{
            // Añadir el taxista que rachazó a la lista
            datosViaje.getTaxistasQueRechazaron().add(principal.getName());

            // Si el que lo solicitó era un operador, se le avisa en caso de que rechazara
            UsuarioEntidad usuarioCliente = new UsuarioEntidad();
            usuarioCliente.setPkCorreo(datosViaje.getCorreoCliente());
            if(usuarioServicio.obtenerTipo(usuarioCliente).equals("Operador")){
                template.convertAndSend("/user/" + datosViaje.getCorreoCliente() + "/queue/esperar-taxista", "Taxista rechazó el viaje");
            }

            // Buscar en el resto de taxistas que quedan
            List<Pair<String, LatLng>> taxistasDisponibles = ubicacionTaxistasServicio.obtenerTaxistasDisponibles(datosViaje.getTaxistasQueRechazaron());
            LatLng origen = new LatLng(Double.parseDouble(datosViaje.getOrigen().split(",")[0]), Double.parseDouble(datosViaje.getOrigen().split(",")[1]));
            try {
                String taxistaEscogido = distanciaServicio.taxistaMasCercano(origen, taxistasDisponibles);
                this.template.convertAndSend("/user/" + taxistaEscogido + "/queue/recibir-viaje", datosViaje);
                return ok("Se le avisó al siguiente taxista " + taxistaEscogido);
            } catch (ApiException | InterruptedException | IOException e) {
                template.convertAndSend("/user/" + datosViaje.getCorreoCliente() + "/queue/esperar-taxista", "No se logró encontrar un taxista");
                throw new UsuarioNoEncontradoExcepcion("No se logró encontrar taxista para el viaje", HttpStatus.NOT_FOUND, System.currentTimeMillis());
            }
        }
    }
}