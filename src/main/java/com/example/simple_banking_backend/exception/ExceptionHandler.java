package com.example.simple_banking_backend.exception;

import com.example.simple_banking_backend.model.Error;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<Object> abstractExceptionHandler(AbstractException ex) {
        return new ResponseEntity<>(new Error(ex.getMessage()), ex.getStatus());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<Object> notFoundExceptionHandler(NotFoundException ex) {
        return new ResponseEntity<>(0, ex.getStatus());
    }

}
