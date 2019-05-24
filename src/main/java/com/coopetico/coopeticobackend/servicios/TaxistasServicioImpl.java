package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.bd.*;
import com.coopetico.coopeticobackend.entidades.*;
import com.coopetico.coopeticobackend.excepciones.UsuarioNoEncontradoExcepcion;
import com.coopetico.coopeticobackend.repositorios.GruposRepositorio;
import com.coopetico.coopeticobackend.repositorios.TaxisRepositorio;
import com.coopetico.coopeticobackend.repositorios.TaxistasRepositorio;
import com.coopetico.coopeticobackend.repositorios.UsuariosRepositorio;
import com.coopetico.coopeticobackend.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

/**
 Servicio de la entidad Taxista.
 @author      Christofer Rodriguez Sanchez.
 @since       16-04-2019.
 @version    1.0.
 */
@CrossOrigin(origins = "http://localhost:4200")
@Service
public class TaxistasServicioImpl implements  TaxistasServicio {

    /**
     * Repositorio de taxistas.
     */
    @Autowired
    public TaxistasRepositorio taxistaRepositorio;

    /**
     * Repositorio de grupos.
     */
    @Autowired
    private GruposRepositorio gruposRepositorio;

    /**
     * Repositorio de usuario.
     */
    @Autowired
    private UsuariosRepositorio usuarioRepositorio;

    /**
     * Repositorio de taxi.
     */
    @Autowired
    private TaxisRepositorio taxiRepositorio;

    /**
     * Repositorio de conduce.
     */
    @Autowired
    private ConduceRepositorio conduceRepositorio;

    /**
     * Id del grupo de Taxistas.
     */
    private final String idGrupoTaxista = "Taxista";

    public TaxistasServicioImpl() { }

    /**
     * Funcion que retorna una lista de los taxis que conduce un taxista.
     * @param taxisSiConduce Lista de ConduceEntidad(taxis) que conduce el taxista
     * @return Lista de placas que el taxistas puede manejar.
     */
    public List<String> taxisConduce(Collection<ConduceEntidad> taxisSiConduce){
        List<String> siConduce = new ArrayList();
        if (taxisSiConduce != null) {
            for (ConduceEntidad taxi : taxisSiConduce) {
                siConduce.add(taxi.getTaxiConducido().getPkPlaca());
            }
        }
        return siConduce;
    }

    /**
     * Funcion que retorna una lista de los taxis que no puede conducir un taxista.
     * @param siConduce Lista de placas que conduce el taxista
     * @return Lista de placas que no conduce el taxistas.
     */
    public List<String> taxisNoConduce(List<String> siConduce){
        List<TaxiEntidad> taxis = this.taxiRepositorio.findAll();
        List<String> noConduce = new ArrayList();
        if (siConduce != null && taxis != null) {
            for (TaxiEntidad taxi : taxis) {
                if (siConduce.indexOf(taxi.getPkPlaca()) == -1) {
                    noConduce.add(taxi.getPkPlaca());
                }
            }
        }
        return noConduce;
    }

    /**
     * Funcion que retorna los taxistas del sistema.
     * @return Lista de taxistas del sistema.
     */
    @Override
    @Transactional
    public List<TaxistaEntidadTemporal> consultar(){
        List<TaxistaEntidad> listaTaxistaEntidad = taxistaRepositorio.findAll();
        List<TaxistaEntidadTemporal> listaTaxistaEntidadTemporal = new ArrayList<>();
        for (TaxistaEntidad taxista : listaTaxistaEntidad) {
            //Sacar los taxis que puede conducir ese taxisa
            List<String> siConduce = taxisConduce(taxista.getTaxisConducidos());
            //Sacar los taxis que no puede conducir ese taxista
            List<String> noConduce = taxisNoConduce(siConduce);
            // Solo se retornan taxistas validos (Bit de valido en true) Controlando el borrado logico
            if(taxista.getUsuarioByPkCorreoUsuario().getValid()){
                listaTaxistaEntidadTemporal.add(new TaxistaEntidadTemporal(taxista, siConduce, noConduce));
            }
        }
        return listaTaxistaEntidadTemporal;
    }

    @Override
    @Transactional
    public Optional<TaxistaEntidad> taxistaPorCorreo(String correo) {
        return taxistaRepositorio.findById(correo);
    }


    /**
     * Funcion que guarda la informacion del taxista que entra por parametro.
     * @param taxistaEntidadTemporal Taxista que se quiere guardar.
     * @param pkCorreoUsuario Antiguo correo del taxista que se quiere guardar.
     * @return Taxista guardado en el sistema.
     */
    @Override
    @Transactional
    public TaxistaEntidadTemporal guardar(TaxistaEntidadTemporal taxistaEntidadTemporal, String pkCorreoUsuario){
        // Se asume que el taxista no es nuevo
        boolean nuevo = false;
        TaxistaEntidad taxistaEntidad = taxistaRepositorio.findById(pkCorreoUsuario)
                .orElse(null);
        //Caso de taxista nuevo
        if (taxistaEntidad == null){
            taxistaEntidad = new TaxistaEntidad();
            taxistaEntidad.setUsuarioByPkCorreoUsuario(new UsuarioEntidad());
            taxistaEntidad.getUsuarioByPkCorreoUsuario().setContrasena("$2a$10$gJ0hUnsEvTp5zyBVo19IHe.GoYKkL3Wy268wGJxG5.k.tUFhSUify");
            nuevo = true;
        }
        // Se cargan los valores que vienen en el taxista del parametro
        taxistaEntidad.setPkCorreoUsuario(taxistaEntidadTemporal.getPkCorreoUsuario());
        taxistaEntidad.setFaltas(taxistaEntidadTemporal.getFaltas());
        taxistaEntidad.setEstado(taxistaEntidadTemporal.isEstado());
        taxistaEntidad.setHojaDelincuencia(taxistaEntidadTemporal.isHojaDelincuencia());
        taxistaEntidad.setEstrellas(taxistaEntidadTemporal.getEstrellas());
        taxistaEntidad.setVence_licencia(taxistaEntidadTemporal.getVence_licencia());
        taxistaEntidad.setJustificacion(taxistaEntidadTemporal.getJustificacion());
        taxistaEntidad.getUsuarioByPkCorreoUsuario().setPkCorreo(taxistaEntidadTemporal.getPkCorreoUsuario());
        taxistaEntidad.getUsuarioByPkCorreoUsuario().setNombre(taxistaEntidadTemporal.getNombre());
        taxistaEntidad.getUsuarioByPkCorreoUsuario().setApellido1(taxistaEntidadTemporal.getApellido1());
        taxistaEntidad.getUsuarioByPkCorreoUsuario().setApellido2(taxistaEntidadTemporal.getApellido2());
        taxistaEntidad.getUsuarioByPkCorreoUsuario().setTelefono(taxistaEntidadTemporal.getTelefono());
        taxistaEntidad.getUsuarioByPkCorreoUsuario().setFoto(taxistaEntidadTemporal.getFoto());
        taxistaEntidad.getUsuarioByPkCorreoUsuario().setValid(taxistaEntidadTemporal.getValid());
        //Se agrega el grupo del taxista
        GrupoEntidad grupoTaxista = this.gruposRepositorio.findById(this.idGrupoTaxista).orElse(null);
        taxistaEntidad.getUsuarioByPkCorreoUsuario().setGrupoByIdGrupo(grupoTaxista);
        // Se guarda el usuario del taxista
        if (nuevo){
            this.usuarioRepositorio.save(taxistaEntidad.getUsuarioByPkCorreoUsuario());
        }
        //Se eliminan los taxis que no conduce ahora
        Collection<ConduceEntidad> taxisAntesConducidos = taxistaEntidad.getTaxisConducidos();
        //Condicion para el caso donde el taxista no es nuevo y puede tener taxis que conduce
        if (taxisAntesConducidos != null) {
            //Se eliminan los taxis que conduce en la tabla Conduce en caso de que se los quitaron
            for (ConduceEntidad conduce : taxisAntesConducidos) {
                if (taxistaEntidadTemporal.getNoConduce().indexOf(conduce.getConduceEntidadPK().getPkPlacaTaxi()) != -1) {
                    this.conduceRepositorio.delete(conduce);
                }
            }
        }
        //Se agregan los taxis que conduce a la tabla conduce y se agregan a la entidad a guardar
        Collection<ConduceEntidad> taxisConducidos = new ArrayList<ConduceEntidad>();
        List<String> temporalSiConduce = taxistaEntidadTemporal.getSiConduce();
        //Condicion para el caso donde si se le asiganan taxis que conduce
        if (temporalSiConduce != null) {
            for (String pkPlacaTaxi : temporalSiConduce) {
                //Se crea el pk de conduce
                ConduceEntidadPK conduceEntidadPK = new ConduceEntidadPK(taxistaEntidad.getPkCorreoUsuario(), pkPlacaTaxi);
                //Se busca el taxista
                TaxistaEntidad taxistaConduce = this.taxistaRepositorio.findById(taxistaEntidad.getPkCorreoUsuario()).orElse(null);
                //Se busca el taxi
                TaxiEntidad taxiConducido = this.taxiRepositorio.findById(pkPlacaTaxi).orElse(null);
                //Se crea la relación
                ConduceEntidad conduce = new ConduceEntidad(conduceEntidadPK, taxistaConduce, taxiConducido);
                //Se añade como taxi que conduce
                taxisConducidos.add(conduce);
                //Se guarda en la tabla
                this.conduceRepositorio.save(conduce);
            }
        }
        //Se le agregan los taxis que conduce al taxista.
        taxistaEntidad.setTaxisConducidos(taxisConducidos);
        //Se guardan los datos.
        TaxistaEntidad retornoSave = taxistaRepositorio.save(taxistaEntidad);
        //Sacar los taxis que puede conducir ese taxisa
        List<String> siConduce = taxisConduce(retornoSave.getTaxisConducidos());
        //Sacar los taxis que no puede conducir ese taxista
        List<String> noConduce = taxisNoConduce(siConduce);
        return new TaxistaEntidadTemporal(retornoSave, siConduce, noConduce);
    }

    /**
     * Funcion que retorna el taxista que indican en el parametro.
     * @param correoUsuario Id del taxista que se quiere consultar
     * @return Taxista solicitado.
     */
    @Override
    @Transactional
    public TaxistaEntidadTemporal consultarPorId(String correoUsuario){
        TaxistaEntidad taxistaEntidad = taxistaRepositorio.findById(correoUsuario).orElse(null);
        TaxistaEntidadTemporal taxistaEntidadTemporal;
        if (taxistaEntidad == null){
            taxistaEntidadTemporal = new TaxistaEntidadTemporal();
        }else{
            //Sacar los taxis que puede conducir ese taxisa
            List<String> siConduce = taxisConduce(taxistaEntidad.getTaxisConducidos());
            //Sacar los taxis que no puede conducir ese taxista
            List<String> noConduce = taxisNoConduce(siConduce);
            if(taxistaEntidad.getUsuarioByPkCorreoUsuario().getValid()) {
                taxistaEntidadTemporal = new TaxistaEntidadTemporal(taxistaEntidad, siConduce, noConduce);
            } else {
                taxistaEntidadTemporal = new TaxistaEntidadTemporal();
            }
        }
        return taxistaEntidadTemporal;
    }

    /**
     * Funcion que retorna el taxista que indican en el parametro.
     * @param correoUsuario Id del taxista que se quiere eliminar
     */
    @Override
    @Transactional
    public void eliminar(String correoUsuario){
        taxistaRepositorio.deleteById(correoUsuario);
        usuarioRepositorio.deleteById(correoUsuario);
    }

    /**
     * Devuelve el estado del taxista
     * @param correo Correo del taxista
     * @return Mapa con el estado del taxista, y en caso de estar bloqueado, la justificacion
     * @author Kevin Jiménez
     */
    @Override
    public  Map<String, Object> obtenerEstado(String correo) throws UsuarioNoEncontradoExcepcion {
        if(!taxistaRepositorio.existsById(correo)){
            throw new UsuarioNoEncontradoExcepcion("El usuario no existe.", HttpStatus.NOT_FOUND, System.currentTimeMillis());
        }
        TaxistaEntidad taxista = taxistaRepositorio.findById(correo).get();
        Map<String, Object> estado = new HashMap<>();
        estado.put("estado", taxista.isEstado());
        estado.put("justificacion", taxista.getJustificacion());
        return estado;
    }

    /**
     * Trae los datos del taxista asociados al correo parametrisado.
     * Se despiega en flutter cuando el usuario ve los datos del cofer asignado.
     *
     * @author Joseph Rementería (b55824)
     * @since 15-05-2019
     *
     * @param correoTaxista correo del taxista asignado
     * @return datos a mostrar en flutter.
     */
    @Override
    @Transactional
    public DatosTaxistaAsigadoEntidad obtenerDatosTaxistaAsignado(String correoTaxista) {
        UsuarioEntidad taxistaUsuarioDatos = this.usuarioRepositorio.findById(correoTaxista).get();
        TaxistaEntidad taxistaPorCorreo = this.taxistaRepositorio.findById(correoTaxista).get();
        return new DatosTaxistaAsigadoEntidad(
            null,  // El viaje se asigna una capa arriba.
            correoTaxista,
            taxistaUsuarioDatos.getNombre()
                + taxistaUsuarioDatos.getApellido1() + taxistaUsuarioDatos.getApellido2(),
            taxistaUsuarioDatos.getFoto(),
            taxistaPorCorreo.getEstrellas()
        );
    }
}
