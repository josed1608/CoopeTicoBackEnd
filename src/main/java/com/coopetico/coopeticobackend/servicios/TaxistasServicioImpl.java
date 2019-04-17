package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.TaxistaEntidad;
import com.coopetico.coopeticobackend.entidades.TaxistaEntidadTemporal;
import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.repositorios.TaxistasRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.transaction.Transactional;
import java.util.List;
import java.util.ArrayList;

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
    private TaxistasRepositorio taxistaRepositorio;

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
            listaTaxistaEntidadTemporal.add(new TaxistaEntidadTemporal(taxista));
        }
        return listaTaxistaEntidadTemporal;
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
        TaxistaEntidad taxistaEntidad = taxistaRepositorio.findById(pkCorreoUsuario)
                .orElse(null);
        if (taxistaEntidad == null){
            taxistaEntidad = new TaxistaEntidad();
            taxistaEntidad.setUsuarioByPkCorreoUsuario(new UsuarioEntidad());
        }
        taxistaEntidad.setPkCorreoUsuario(taxistaEntidadTemporal.getPkCorreoUsuario());
        taxistaEntidad.setFaltas(taxistaEntidadTemporal.getFaltas());
        taxistaEntidad.setEstado(taxistaEntidadTemporal.isEstado());
        taxistaEntidad.setHojaDelincuencia(taxistaEntidadTemporal.isHojaDelincuencia());
        taxistaEntidad.setEstrellas(taxistaEntidadTemporal.getEstrellas());
        taxistaEntidad.getUsuarioByPkCorreoUsuario().setNombre(taxistaEntidadTemporal.getNombre());
        taxistaEntidad.getUsuarioByPkCorreoUsuario().setApellidos(taxistaEntidadTemporal.getApellidos());
        taxistaEntidad.getUsuarioByPkCorreoUsuario().setTelefono(taxistaEntidadTemporal.getTelefono());
        taxistaEntidad.getUsuarioByPkCorreoUsuario().setFoto(taxistaEntidadTemporal.getFoto());

        TaxistaEntidad retornoSave = taxistaRepositorio.save(taxistaEntidad);
        return new TaxistaEntidadTemporal(retornoSave);
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
            taxistaEntidadTemporal = new TaxistaEntidadTemporal(taxistaEntidad);
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
    }

}
