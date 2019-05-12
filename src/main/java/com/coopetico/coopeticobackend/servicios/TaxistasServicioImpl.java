package com.coopetico.coopeticobackend.servicios;

import com.coopetico.coopeticobackend.entidades.*;
import com.coopetico.coopeticobackend.repositorios.GruposRepositorio;
import com.coopetico.coopeticobackend.repositorios.TaxisRepositorio;
import com.coopetico.coopeticobackend.repositorios.TaxistasRepositorio;
import com.coopetico.coopeticobackend.repositorios.UsuariosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.transaction.TransactionScoped;
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
     * Id del grupo de Taxistas.
     */
    private final String idGrupoTaxista = "Taxista";

    public TaxistasServicioImpl() { }

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
        boolean nuevo = false;
        TaxistaEntidad taxistaEntidad = taxistaRepositorio.findById(pkCorreoUsuario)
                .orElse(null);
        if (taxistaEntidad == null){
            taxistaEntidad = new TaxistaEntidad();
            taxistaEntidad.setUsuarioByPkCorreoUsuario(new UsuarioEntidad());
            nuevo = true;
        }
        taxistaEntidad.setPkCorreoUsuario(taxistaEntidadTemporal.getPkCorreoUsuario());
        taxistaEntidad.setFaltas(taxistaEntidadTemporal.getFaltas());
        taxistaEntidad.setEstado(taxistaEntidadTemporal.isEstado());
        taxistaEntidad.setHojaDelincuencia(taxistaEntidadTemporal.isHojaDelincuencia());
        taxistaEntidad.setEstrellas(taxistaEntidadTemporal.getEstrellas());
        taxistaEntidad.getUsuarioByPkCorreoUsuario().setPkCorreo(taxistaEntidadTemporal.getPkCorreoUsuario());
        taxistaEntidad.getUsuarioByPkCorreoUsuario().setNombre(taxistaEntidadTemporal.getNombre());
        taxistaEntidad.getUsuarioByPkCorreoUsuario().setApellido1(taxistaEntidadTemporal.getApellido1());
        taxistaEntidad.getUsuarioByPkCorreoUsuario().setApellido2(taxistaEntidadTemporal.getApellido2());
        taxistaEntidad.getUsuarioByPkCorreoUsuario().setTelefono(taxistaEntidadTemporal.getTelefono());
        taxistaEntidad.getUsuarioByPkCorreoUsuario().setFoto(taxistaEntidadTemporal.getFoto());
        taxistaEntidad.getUsuarioByPkCorreoUsuario().setContrasena("$2a$10$gJ0hUnsEvTp5zyBVo19IHe.GoYKkL3Wy268wGJxG5.k.tUFhSUify");
        GrupoEntidad grupoTaxista = this.gruposRepositorio.findById(this.idGrupoTaxista).orElse(null);
        taxistaEntidad.getUsuarioByPkCorreoUsuario().setGrupoByIdGrupo(grupoTaxista);
        if (nuevo){
            this.usuarioRepositorio.save(taxistaEntidad.getUsuarioByPkCorreoUsuario());
        }
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
        usuarioRepositorio.deleteById(correoUsuario);
    }

    /**
     * Trae de la base la entidad taxista identificada al corro dado
     *
     * @author Joseph Rementeríá (b55824)
     * @since 11-05-2019
     *
     * @param correo el correo del usuario
     * @return la entidad si existe, null de otra manera
     */
    @Override
    @Transactional
    public TaxistaEntidad consultarTaxistaPorId(String correo) {
        return this.taxistaRepositorio.findById(correo).orElse(null);
    }
}
