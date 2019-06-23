//-----------------------------------------------------------------------------
package com.coopetico.coopeticobackend.controladores;
//-----------------------------------------------------------------------------
import com.coopetico.coopeticobackend.entidades.DatosTaxistaAsigadoEntidad;
import com.coopetico.coopeticobackend.entidades.ViajeComenzandoEntidad;
import com.coopetico.coopeticobackend.entidades.ViajeEntidadTemporal;
import com.coopetico.coopeticobackend.entidades.bd.UsuarioEntidad;
import com.coopetico.coopeticobackend.excepciones.UsuarioNoEncontradoExcepcion;
import com.coopetico.coopeticobackend.servicios.*;
import com.coopetico.coopeticobackend.entidades.bd.ViajeEntidad;
import com.coopetico.coopeticobackend.servicios.UsuarioServicio;
import com.coopetico.coopeticobackend.entidades.ViajeDatosIniciales;
import com.coopetico.coopeticobackend.servicios.ViajesServicio;
import com.coopetico.coopeticobackend.entidades.ViajeTmpEntidad;

import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
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
 * Este es el controlador Viajes. Se encarga de la comunicación entre
 * la capa V y el M viajes.
 *
 * @autor Joseph Rementería (b55824)
 * @since 06-04-2019
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
     * Constructor de la clase.
     *
     * @author Joseph Rementería (b55824)
     * @since 06-04-2019
     *
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
     * Guarda una tupla en la base de datos.
     *
     * @author Joseph Rementería (b55824)
     * @since 06-04-2019
     *
     * @param viajeTempEntidad  entidad dummy crada para recibir los datos
     *                          del viaje del front end
     * @return String se inserto, null de otra manera.
     */
    @PostMapping("viajeCompleto")
    public ResponseEntity agregarViaje (@RequestBody ViajeTmpEntidad viajeTempEntidad) {
        try  {
            this.viajesServicio.guardar(
                viajeTempEntidad.getPlaca(),
                viajeTempEntidad.getCorreoCliente(),
                viajeTempEntidad.getFechaInicio(),
                viajeTempEntidad.getFechaFin(),
                viajeTempEntidad.getCosto(),
                viajeTempEntidad.getEstrellas(),
                viajeTempEntidad.getOrigen(),
                viajeTempEntidad.getDestino(),
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
    @PostMapping("/solicitar")
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
    public ResponseEntity respuestaTaxista(Principal principal, @RequestParam boolean respuesta, @RequestBody ViajeComenzandoEntidad datosViaje) {
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
            if(usuarioServicio.obtenerTipo(usuarioCliente).equals("operador")){
                template.convertAndSend("/user/" + datosViaje.getCorreoCliente() + "/queue/esperar-taxista", "Taxista rechazó el viaje");
                return ok("Se le avisó al siguiente taxista");
            }
            else {
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

    /**
     * Crea un registro en la base de datos con los datos del viaej disponibles
     * para cuando se cominza un viaje.
     *
     * @author Joseph Rementería (b55824)
     * @since 11-05-2019
     *
     * @param datosDelViaje todos los datos del viaje
     * @return  Ok si no hubo problema,
     *          Not found si el usuario que crea el viaje no existe en la DB
     *          Server error, si el error no ha sido identificado.
     */
    @PostMapping()
    public ResponseEntity crearViaje(@RequestBody ViajeDatosIniciales datosDelViaje) {
        //---------------------------------------------------------------------
        ResponseEntity result = null;
        //---------------------------------------------------------------------
        // Se intenta insertar la entidad en la base de datos
        int respuestaRepo = this.viajesServicio.crear(
            datosDelViaje.getPlaca(),
            datosDelViaje.getFechaInicio(),
            datosDelViaje.getCorreoCliente(),
            datosDelViaje.getOrigen(),
            datosDelViaje.getCorreoTaxista()
        );
        //---------------------------------------------------------------------
        // Se genera la respuesta HTTP correspondiente al código de error/éxito
        switch (respuestaRepo){
            case 0:
                result = new ResponseEntity(
                        "Se insetó el viaje",
                        HttpStatus.OK
                );
                break;
            case -1:
                result = new ResponseEntity(
                        "Hubo un error no identificado",
                        HttpStatus.INTERNAL_SERVER_ERROR
                );
                break;
            case -2:
                result = new ResponseEntity(
                        "El usuario (cliente ni operador) no existe",
                        HttpStatus.NOT_FOUND
                );
                break;
            case -3:
                result = new ResponseEntity(
                  "No se se pudo insertar en la base",
                  HttpStatus.INTERNAL_SERVER_ERROR
                );
            case -4:
                result = new ResponseEntity(
                    "El dato ya ha sido insertado previamente",
                    HttpStatus.CONFLICT
                );
                break;
        }
        //---------------------------------------------------------------------
        return result;
        //---------------------------------------------------------------------
    }
    //-------------------------------------------------------------------------

    /**
     * Actualiza el campo de fechaFin de la tupla del viaje proporcionado.
     *
     * @author Marco Venegas (B67697)
     * @since 27-05-2019
     *
     * @param   datosDelViaje Placa, fecha de inicio y fecha de finalizacion del viaje.
     * @return  Ok si no hubo problema,
     *          Not found si el viaje no existe en la BD
     *          Conflict, si se intenta finalizar un viaje que ya finalizó.
     *          Forbidden si la fecha de finalización tiene un formato inválido.
     *          Server error, si el error no ha sido identificado o si no se pudo almacenar en la BD.
     */
    @PutMapping("/finalizar")
    public ResponseEntity finalizarViaje(@RequestBody ViajeTmpEntidad datosDelViaje) {
        ResponseEntity resultado = null;
        int respuestaServicio = viajesServicio.finalizar(
                datosDelViaje.getPlaca(),
                datosDelViaje.getFechaInicio(),
                datosDelViaje.getFechaFin()
        );

        switch (respuestaServicio){
            case 0: {
                resultado = new ResponseEntity("Se finalizó el viaje.", HttpStatus.OK);
            }break;

            case -1: {
                resultado = new ResponseEntity("Hubo un error no identificado", HttpStatus.INTERNAL_SERVER_ERROR);
            }break;

            case -2: {
                resultado = new ResponseEntity("No existe este viaje en la base de datos", HttpStatus.NOT_FOUND);
            }break;

            case -3: {
                resultado = new ResponseEntity("No se pudo finalizar este viaje puesto que ya finalizó", HttpStatus.CONFLICT);
            }break;

            case -4: {
                resultado = new ResponseEntity("El formato de fecha de finalización es inválido.", HttpStatus.FORBIDDEN);
            }break;

            case -5: {
                resultado = new ResponseEntity("El viaje no puede finalizar antes de que comenzó.", HttpStatus.FORBIDDEN);
            }break;

            case -6: {
                resultado = new ResponseEntity("No se pudo actualizar el estado del viaje", HttpStatus.INTERNAL_SERVER_ERROR);
            }break;
        }
        return resultado;
    }

    /**
     * Actualiza el campo de fechaFin de la tupla del viaje proporcionado.
     *
     * @author Marco Venegas (B67697)
     * @since 27-05-2019
     *
     * @param   datosDelViaje Placa, fecha de inicio y estrellas para el viaje.
     * @return  Ok si no hubo problema,
     *          Not found si el viaje no existe en la BD
     *          Conflict, si se intenta asignar estrellas a un viaje que no ha finalizado.
     *          Forbidden si la cantidad de estrellas es inválida.
     *          Server error, si el error no ha sido identificado o si no se pudo almacenar en la BD.
     */
    @PutMapping("/asignarEstrellas")
    public ResponseEntity asignarEstrellasViaje(@RequestBody ViajeTmpEntidad datosDelViaje) {
        ResponseEntity resultado = null;
        int respuestaServicio = viajesServicio.asignarEstrellas(
                datosDelViaje.getPlaca(),
                datosDelViaje.getFechaInicio(),
                datosDelViaje.getEstrellas()
        );

        switch (respuestaServicio){
            case 0: {
                resultado = new ResponseEntity("Se asignaron las estrellas para el viaje.", HttpStatus.OK);
            }break;

            case -1: {
                resultado = new ResponseEntity("Hubo un error no identificado", HttpStatus.INTERNAL_SERVER_ERROR);
            }break;

            case -2: {
                resultado = new ResponseEntity("No existe este viaje en la base de datos", HttpStatus.NOT_FOUND);
            }break;

            case -3: {
                resultado = new ResponseEntity("No se pudo asignar estrellas a este viaje puesto que no ha finalizado", HttpStatus.CONFLICT);
            }break;

            case -4: {
                resultado = new ResponseEntity("No se pueden asignar menos de 1 ni más de 5 estrellas", HttpStatus.FORBIDDEN);
            }break;

            case -5: {
                resultado = new ResponseEntity("No se pudo asignar estrellas al viaje por un problema con la BD", HttpStatus.INTERNAL_SERVER_ERROR);
            }break;
        }
        return resultado;
    }
}
//-----------------------------------------------------------------------------