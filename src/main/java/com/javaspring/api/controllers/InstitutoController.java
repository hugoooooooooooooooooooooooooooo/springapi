package com.javaspring.api.controllers;

import com.javaspring.api.model.Instituto;
import com.javaspring.api.services.InstitutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class InstitutoController {
    @Autowired
    private InstitutoService institutoService;


    @GetMapping("/institutosview")
    public ModelAndView listado(Model modelo) throws UnsupportedEncodingException {
        List<Instituto> institutos = getAllInstitutos();

        modelo.addAttribute("listaInstitutos", institutos);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("listadoinsti.html");
        return modelAndView;
    }

    @Operation(summary = "Obtiene todos los Institutos", description = "Obtiene una lista de Instituto", tags = {"institutos"})
    @ApiResponse(responseCode = "200", description = "Lista de Instituto")
    @GetMapping("/instituto")
    public List<Instituto> getAllInstitutos() {
        return institutoService.getAllInstitutos();
    }

    @Operation(summary = "Obtiene un instituto", description = "Obtiene un instituto dado su id", tags = {"institutos"})
    @Parameter(name = "id", description = "ID del instituto", required = true, example = "1")
    @ApiResponse(responseCode = "200", description = "instituto encontrado")
    @ApiResponse(responseCode = "404", description = "instituto no encontrado")
    @GetMapping("/instituto/{id}")
    public ResponseEntity<Instituto> getInstitutoById(@PathVariable Long id) {
        Optional<Instituto> optionalInstituto = institutoService.getInstitutoById(id);

        if (((Optional<?>) optionalInstituto).isPresent()) {
            optionalInstituto = institutoService.getInstitutoById(id);
            return new ResponseEntity<>(optionalInstituto.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}

