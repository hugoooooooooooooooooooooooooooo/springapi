package com.javaspring.api.services;


import com.javaspring.api.exceptions.ProfesorBadRequestException;
import com.javaspring.api.exceptions.ProfesorNotFoundException;
import com.javaspring.api.model.Instituto;
import com.javaspring.api.model.Profesor;
import com.javaspring.api.repositories.ProfesorRepository;
import com.javaspring.api.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProfesorService {
    @Autowired
    private ProfesorRepository profesorRepository;
    public List<Profesor> getAllProfesores() {
        return profesorRepository.findAll();
    }
    public Profesor createProfesor(Profesor profesor, MultipartFile file) throws IOException {
        if (profesor.getNombre() == null || profesor.getNombre().isEmpty())
            throw new ProfesorBadRequestException("Debe introducirse el nombre");

        if (profesor.getEdad() == null || profesor.getEdad() <= 0)
            throw new ProfesorBadRequestException("Debe introducirse la edad y debe ser mayor que 0");

        Long idInstituto = profesor.getInstituto().getId();

        if (idInstituto == null || idInstituto <= 0 || idInstituto > 5)
            throw new ProfesorBadRequestException("Debe introducirse valor de género entre 1 y 5");

        String instituto;
        if ( idInstituto == 1)
            instituto = "IES Palomeras";
        else if ( idInstituto == 2)
            instituto = "IES Villa";
        else if ( idInstituto == 3)
            instituto = "IES Madrid-Sur";
        else if ( idInstituto == 4)
            instituto = "Maria Rodrigo";
        else
            instituto = "IES fuera vk";

        Profesor profesorsave = new Profesor(profesor.getNombre(), profesor.getEdad(), new Instituto(idInstituto, instituto));

        if (!file.isEmpty()) {
            profesorsave.setImagen(file.getOriginalFilename());
            profesorsave.setFoto(ImageUtils.compressImage(file.getBytes()));
        }
        else
            throw new ProfesorBadRequestException("Debe introducirse el fichero imagen");

        return profesorRepository.save(profesorsave);
    }

    public Optional<Profesor> getProfesorById(Long id) {
        return Optional.ofNullable(profesorRepository.findById(id).orElseThrow(
                () -> new ProfesorNotFoundException("No se ha encontrado la persona con id: " + id)
                ));

    }
    public Profesor updateProfesor(Profesor profesor, MultipartFile file) throws IOException {
        if (profesor.getNombre() == null || profesor.getNombre().isEmpty())
            throw new ProfesorBadRequestException("Debe introducirse el nombre");

        if (profesor.getEdad() == null || profesor.getEdad() <= 0)
            throw new ProfesorBadRequestException("Debe introducirse la edad y debe ser mayor que 0");

        Long idInstituto = profesor.getInstituto().getId();

        if (idInstituto == null || idInstituto <= 0 || idInstituto > 5)
            throw new ProfesorBadRequestException("Debe introducirse valor de género entre 1 y 5");

        String instituto;
        if ( idInstituto == 1)
            instituto = "IES Palomeras-Vallecas";
        else if ( idInstituto == 2)
            instituto = "IES Villa de Vallecas";
        else if ( idInstituto == 3)
            instituto = "IES Madrid-Sur";
        else if ( idInstituto == 4)
            instituto = "IES Maria Rodrigo";
        else
            instituto = "IES de fuera de vallecas";

        Profesor profesorsave = new Profesor(profesor.getNombre(), profesor.getEdad(), new Instituto(idInstituto, instituto));

        if (!file.isEmpty()) {
            profesor.setImagen(file.getOriginalFilename());
            profesor.setFoto(ImageUtils.compressImage(file.getBytes()));
        }
        else
            throw new ProfesorBadRequestException("Debe introducirse el fichero imagen");

        return profesorRepository.save(profesor);
    }

    public void deleteProfesorById(Long id) {
        profesorRepository.deleteById(id);
    }
    public List<Profesor> getProfesoresByNombre(String nombre){ return profesorRepository.findByNombreContainingIgnoreCase(nombre);}

    public byte[] descargarFoto(Long id){
        Profesor profesor = profesorRepository.findById(id).orElse(null);
        return profesor != null ? ImageUtils.decompressImage(profesor.getFoto()) : null;
    }
}
