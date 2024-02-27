package com.javaspring.api.controllers;

import com.javaspring.api.model.Instituto;
import com.javaspring.api.model.Profesor;
import com.javaspring.api.services.InstitutoService;
import com.javaspring.api.services.ProfesorService;
import com.javaspring.api.util.ImageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProfesorController {
    @Autowired
    private ProfesorService profesorService;

    @Autowired
    private InstitutoService institutoService;

    @GetMapping("/profesoresview")
    public ModelAndView listado(Model modelo) throws UnsupportedEncodingException {
        List<Profesor> profesores = getAllProfesores();

        modelo.addAttribute("listaProfesores", profesores);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("listado.html");
        return modelAndView;
    }
    @Operation(summary = "Obtiene todas los Profesores", description = "Obtiene una lista de Profesores", tags = {"profesores"})
    @ApiResponse(responseCode = "200", description = "Lista de Profesores")
    @GetMapping("/profesor")
    public List<Profesor> getAllProfesores() {
        return profesorService.getAllProfesores();
    }

    @Operation(summary = "Crea un profesor", description = "Crea un profesor asociado a un instituto con id del 1 al 5", tags = {"profesores"})
    @ApiResponse(responseCode = "200", description = "profesor encontrado")
    @ApiResponse(responseCode = "404", description = "profesor no encontrado")
    @PostMapping(value = "/profesor", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Profesor> createProfesor(@RequestParam String nombre, @RequestParam Integer edad,@RequestParam Long id_instituto, @RequestPart(name="imagen", required=false) MultipartFile imagen) throws IOException {

        Instituto instituto = new Instituto();
        Optional<Instituto> optionalInstituto = institutoService.getInstitutoById(id_instituto);
        if (((Optional<?>) optionalInstituto).isPresent()) {
            instituto = optionalInstituto.get();
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Profesor createdProfesor = profesorService.createProfesor(new Profesor(nombre,edad, instituto),imagen);
        return new ResponseEntity<>(createdProfesor, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene un profesor", description = "Obtiene un profesor dado su id", tags = {"profesores"})
    @Parameter(name = "id", description = "ID del profesor", required = true, example = "1")
    @ApiResponse(responseCode = "200", description = "profesor encontrado")
    @ApiResponse(responseCode = "404", description = "profesor no encontrado")
    @GetMapping("/profesor/{id}")
    public ResponseEntity<Profesor> getProfesorById(@PathVariable Long id) {
        Optional<Profesor> optionalProfesor = profesorService.getProfesorById(id);
        if (((Optional<?>) optionalProfesor).isPresent()) {
            optionalProfesor = profesorService.getProfesorById(id);
            return new ResponseEntity<>(optionalProfesor.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Edita un profesor", description = "Edita un profesor dado su id", tags = {"profesores"})
    @Parameter(name = "id", description = "ID del profesor", required = true, example = "1")
    @ApiResponse(responseCode = "200", description = "profesor encontrado")
    @ApiResponse(responseCode = "404", description = "profesor no encontrado")
    @PutMapping(value = "/profesor/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Profesor> updateProfesor(@PathVariable Long id, @RequestParam String nombre, @RequestParam Integer edad,@RequestParam Long id_instituto, @RequestPart(name="imagen", required=false) MultipartFile imagen) throws IOException {

        Instituto instituto = new Instituto();
        Optional<Instituto> optionalInstituto = institutoService.getInstitutoById(id_instituto);
        if (((Optional<?>) optionalInstituto).isPresent()) {
            instituto = optionalInstituto.get();
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<Profesor> optionalProfesor = profesorService.getProfesorById(id);
        if (((Optional<?>) optionalProfesor).isPresent()) {
            Profesor existingProfesor = optionalProfesor.get();
            existingProfesor.setNombre(nombre);
            existingProfesor.setEdad(edad);
            existingProfesor.setFoto(ImageUtils.compressImage(imagen.getBytes()));

            Profesor updatedProfesor = profesorService.updateProfesor(existingProfesor, imagen);
            return new ResponseEntity<>(updatedProfesor, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Borra un profesor", description = "Borra un profesor dado su id", tags = {"profesores"})
    @Parameter(name = "id", description = "ID del profesor", required = true, example = "1")
    @ApiResponse(responseCode = "200", description = "profesor encontrado")
    @ApiResponse(responseCode = "404", description = "profesor no encontrado")
    @DeleteMapping("/profesor/{id}")
    public ResponseEntity<Void> deleteProfesor(@PathVariable Long id) {
        Optional<Profesor> optionalProfesor = profesorService.getProfesorById(id);
        if (optionalProfesor.isPresent()) {
            profesorService.deleteProfesorById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Obtiene los profesores por nombre", description = "Obtiene los profesores dado su nombre", tags = {"profesores"})
    @Parameter(name = "nombre", description = "Nombre del profesor", required = true, example = "1")
    @ApiResponse(responseCode = "200", description = "profesor encontrado")
    @ApiResponse(responseCode = "404", description = "profesor no encontrado")
    @GetMapping("/profesor/nom")
    public ResponseEntity<List<Profesor>>
    getProfesoresPorNombre(@RequestParam String nombre) {
        List<Profesor> profesores =
                profesorService.getProfesoresByNombre(nombre);
        if (!profesores.isEmpty()) {
            return new ResponseEntity<>(profesores, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Muestra foto", description = "Obtiene foto de profesor dado el id", tags = {"profesores"})
    @Parameter(name = "id", description = "ID del Profesor", required = true, example = "13")
    @ApiResponse(responseCode = "200", description = "Foto del profesor")
    @ApiResponse(responseCode = "404", description = "Profesor no encontrado")
    @GetMapping(value = "/{id}/foto", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> descargarFoto(@PathVariable Long id) {
        byte[] foto = profesorService.descargarFoto(id);
        if ( foto != null ) {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(foto);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}