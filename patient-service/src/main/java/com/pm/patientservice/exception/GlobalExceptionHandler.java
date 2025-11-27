package com.pm.patientservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice //mark the class as a global handler which receive all the errors from all controllers
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //general scope: the class handle validation errors which appears in the app
    @ExceptionHandler(MethodArgumentNotValidException.class) //this exception occurs when validation of data sent in requests fails
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex){

        Map<String, String> errors = new HashMap<>();

        //obtain all the fields that failed validation. For each error, the map is populated with a key and a value
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        //return a http response with status code of 400 (json with key-value pairs)
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex){
        log.warn("Email address already exists {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();

        errors.put("message", "Email already exists");
        return ResponseEntity.badRequest().body(errors);
    }
}
