package com.coopetico.coopeticobackend.controladores;

import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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

@CrossOrigin
@RestController
@RequestMapping(path="/utilidades")
@Validated
public class UtilidadesControlador {

     // private final Logger log = (Logger) LoggerFactory.getLogger(UtilidadesControlador.class);


    /**
     * Metodo para eliminar una foto
     * @param nombreFotoAnterior Nombre de la foto  a eliminar
     */
    public void eliminarFoto(String nombreFotoAnterior){
        System.out.println("\n\n ELIMINAR " + nombreFotoAnterior + "\n\n");
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
    public ResponseEntity<?> verFoto(@PathVariable String nombreFoto) throws MalformedURLException {
        Path rutaArchivo = Paths.get("images").resolve(nombreFoto).toAbsolutePath();
        Resource recurso = null;
        HttpHeaders cabecera = new HttpHeaders();

        if(nombreFoto!=null) {
            try {
                recurso = new UrlResource(rutaArchivo.toUri());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            assert recurso != null;
            if (!recurso.exists() && !recurso.isReadable()) {
                rutaArchivo = Paths.get("images").resolve("defaultImage.jpg").toAbsolutePath();
                recurso = new UrlResource(rutaArchivo.toUri());
            }
            cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= \"" + recurso.getFilename() + "\"");

        } else {
            rutaArchivo = Paths.get("images").resolve("defaultImage.jpg").toAbsolutePath();
            recurso = new UrlResource(rutaArchivo.toUri());
        }

        return new ResponseEntity<>(recurso, cabecera, HttpStatus.OK);
    }
}
