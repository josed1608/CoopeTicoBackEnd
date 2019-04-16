package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.TaxistaEntidad;
import com.coopetico.coopeticobackend.repositorios.TaxistasRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.transaction.Transactional;
import java.util.List;

/**
 Servicio de la entidad Taxista.
 @author      Christofer Rodriguez Sanchez.
 @since       16-04-2019.
 @version:    1.0.
 */
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
    @CrossOrigin(origins = "http://localhost:4200")
    public List<TaxistaEntidad> consultar(){
        return taxistaRepositorio.consultar();
    }

    /**
     * Funcion que guarda la informacion del taxista que entra por parametro.
     * @param taxista Taxista que se quiere guardar
     * @return Taxista guardado en el sistema.
     */
    @Override
    @Transactional
    public TaxistaEntidad guardar(TaxistaEntidad taxista){
        return taxistaRepositorio.save(taxista);
    }

    /**
     * Funcion que retorna el taxista que indican en el parametro.
     * @param correoUsuario Id del taxista que se quiere consultar
     * @return Taxista solicitado.
     */
    @Override
    @Transactional
    public TaxistaEntidad consultarPorId(String correoUsuario){
        return taxistaRepositorio.findById(correoUsuario).orElse(null);
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
