package com.javaspring.api.services;

import com.javaspring.api.exceptions.InstitutoNotFoundException;
import com.javaspring.api.model.Instituto;
import com.javaspring.api.repositories.InstitutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstitutoService {
    @Autowired
    private InstitutoRepository institutoRepository;

    public List<Instituto> getAllInstitutos() {
        return institutoRepository.findAll();
    }

    public Optional<Instituto> getInstitutoById(Long id) {

        return Optional.ofNullable(institutoRepository.findById(id).orElseThrow(
                () -> new InstitutoNotFoundException("No se ha encontrado el instituto con id: " + id)
        ));
    }


}
