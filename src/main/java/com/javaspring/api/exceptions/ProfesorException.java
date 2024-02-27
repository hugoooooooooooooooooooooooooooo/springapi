package com.javaspring.api.exceptions;
public abstract class ProfesorException extends RuntimeException {
    public ProfesorException(String mensaje) {
        super(mensaje);
    }
}