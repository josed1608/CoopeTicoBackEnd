package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.bd.TaxiEntidad;
import com.coopetico.coopeticobackend.repositorios.TaxisRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Implementación del servicio de taxis
 */

@Service
public class TaxisServicioImpl implements TaxisServicio{
    @Autowired
    private TaxisRepositorio taxisRepositorio;

    /**
     * Método para consultar todos los taxis del sistema
     * @return Lista de entidades taxi
     */
    @Override
    @Transactional
    @CrossOrigin(origins = "http://localhost:4200")
    public List<TaxiEntidad> consultar(){
        return (List<TaxiEntidad>) taxisRepositorio.getTaxisValidos();
    }

    /**
     * Método para consultar un taxi en específico de la base de datos
     * @param placa placa del taxi a consultar
     * @return Entidad taxi
     */
    @Override
    @Transactional
    public TaxiEntidad consultarPorId(String placa){
        return taxisRepositorio.findById(placa).orElse(null);
    }

    /**
     * Método para guardar un taxi en la base de datos. Se usa tanto para agregar un taxi nuevo y para modificar uno existente
     * @param taxi Entidad taxi que se quiere guardar
     * @return Entidad taxi guardada
     */
    @Override
    @Transactional
    public TaxiEntidad guardar(TaxiEntidad taxi){
        return taxisRepositorio.save(taxi);
    }

    /**
     * Método para guardar una lista de taxis en la base de datos.
     * @param taxis Lista Entidad taxi que se quiere guardar
     * @return true si es correcto o false si falla
     * @Transactional rollbackOn, la transaccion hace rollback si se detecta una excepcion
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean guardarLista(List<TaxiEntidad> taxis){
        try{
            for (TaxiEntidad taxi: taxis) {
                 taxisRepositorio.save(taxi);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * Método para eliminar un taxi de la base de datos
     * @param placa placa del taxi a eliminar
     */
    @Override
    @Transactional
    public void eliminar(String placa){
        taxisRepositorio.deleteById(placa);
    }
}
