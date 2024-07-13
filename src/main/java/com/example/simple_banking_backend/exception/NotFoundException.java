package com.example.simple_banking_backend.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NotFoundException extends AbstractException {

    public NotFoundException(String message) {
        super(message, NOT_FOUND);
    }

}
