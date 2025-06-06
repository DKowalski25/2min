package com.github.DKowalski25._min.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Thrown when business rules or validation constraints are violated.
 */
public class BusinessValidationException extends ApplicationException {
    public BusinessValidationException(String message) {
        super(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public BusinessValidationException(String field, String problem) {
        super(String.format("Validation error for field '%s': %s", field, problem),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }
}