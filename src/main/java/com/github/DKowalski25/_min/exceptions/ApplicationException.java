package com.github.DKowalski25._min.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Base exception for all application-specific exceptions.
 * Contains HTTP status code and message for proper error handling.
 */
@Getter
public abstract class ApplicationException extends RuntimeException {
    private final HttpStatus httpStatus;

    public ApplicationException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public ApplicationException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }
}