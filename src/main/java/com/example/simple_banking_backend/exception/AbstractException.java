package com.example.simple_banking_backend.exception;

import org.springframework.http.HttpStatus;

public abstract class AbstractException extends RuntimeException {

    private final HttpStatus status;

    protected AbstractException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

}
