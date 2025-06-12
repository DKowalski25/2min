package com.github.DKowalski25._min.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Thrown when requested entity is not found in the system.
 */
public class EntityNotFoundException extends ApplicationException {
    public EntityNotFoundException(String entityName, Object identifier) {
        super(String.format("%s not found with identifier: %s", entityName, identifier),
                HttpStatus.NOT_FOUND);
    }

    public EntityNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}