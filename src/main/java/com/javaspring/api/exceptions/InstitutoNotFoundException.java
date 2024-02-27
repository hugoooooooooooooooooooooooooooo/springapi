package com.javaspring.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InstitutoNotFoundException extends InstitutoException{
    public InstitutoNotFoundException(String mensaje) {
        super(mensaje);
    }
}
