package com.javaspring.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
// Nos permite devolver un estado cuando salta la excepción
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProfesorBadRequestException extends ProfesorException {
    public ProfesorBadRequestException(String mensaje) {
        super(mensaje);
    }
}
