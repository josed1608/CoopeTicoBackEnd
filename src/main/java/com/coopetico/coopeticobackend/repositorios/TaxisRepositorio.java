package com.coopetico.coopeticobackend.repositorios;

import com.coopetico.coopeticobackend.entidades.bd.TaxiEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Repositorio de la entidad taxi.
 * @author Jorge Araya González
 */
@CrossOrigin(origins = "http://localhost:4200")
public interface TaxisRepositorio extends JpaRepository<TaxiEntidad, String> {
    @Query("select new TaxiEntidad (t.pkPlaca as pkPlaca, t.datafono as datafono, t.telefono as telefono, t.clase as clase, t.tipo as tipo, t.fechaVenRtv as fechaVenRtv, " +
            "t.fechaVenMarchamo as fechaVenMarchamo, t.fechaVenSeguro as fechaVenSeguro, t.valido as valido, t.foto as foto, t.correoTaxista as correoTaxista) from TaxiEntidad t" +
            " where t.valido = true")
    List<TaxiEntidad> getTaxisValidos();
}