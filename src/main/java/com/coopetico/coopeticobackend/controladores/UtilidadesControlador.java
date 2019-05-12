package com.coopetico.coopeticobackend.controladores;

import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 Controlador de utilidades, para colocar métodos que se utilizan en varias partes del código.
 @author     Hannia Aguilar Salas
 @since      12-05-2019
 @version:   1.0
 */

public class UtilidadesControlador {

     // private final Logger log = (Logger) LoggerFactory.getLogger(UtilidadesControlador.class);


    /**
     * Metodo para eliminar una foto
     * @param nombreFotoAnterior Nombre de la foto  a eliminar
     */
    public void eliminarFoto(String nombreFotoAnterior){
        if( nombreFotoAnterior != null && nombreFotoAnterior.length()>0){
            Path rutaArchivoAnterior = Paths.get("images").resolve(nombreFotoAnterior).toAbsolutePath();
            File archivoFotoAnterior = rutaArchivoAnterior.toFile();
            if(archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()){
                archivoFotoAnterior.delete();
            }
        }
    }

    /**
     * Metodo para obtener una imagen
     * @param nombreFoto Nombre de la imagen
     * @return Imagen
     */
    @GetMapping("/uploads/img/{nombreFoto:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){
        Path rutaArchivo = Paths.get("images").resolve(nombreFoto).toAbsolutePath();
        Resource recurso = null;
        Logger log = (Logger) LoggerFactory.getLogger(UtilidadesControlador.class);
        log.info(rutaArchivo.toString());
        try {
            recurso = new UrlResource(rutaArchivo.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if(!recurso.exists()&& !recurso.isReadable()){
            throw new RuntimeException("No se pudo cargar la imagen: "+ nombreFoto);
        }

        HttpHeaders cabecera = new HttpHeaders();
        cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= \""+recurso.getFilename()+"\"");
        return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
    }
}
