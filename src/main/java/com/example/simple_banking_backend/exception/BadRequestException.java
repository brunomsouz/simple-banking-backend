package com.example.simple_banking_backend.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class BadRequestException extends AbstractException {

    public BadRequestException(String message) {
        super(message, BAD_REQUEST);
    }

}
