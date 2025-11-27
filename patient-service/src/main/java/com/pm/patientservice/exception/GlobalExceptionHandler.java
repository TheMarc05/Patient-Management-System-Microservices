package com.pm.patientservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice //mark the class as a global handler which receive all the errors from all controllers
public class GlobalExceptionHandler {

    //general scope: the class handle validation errors which appears in the app
    @ExceptionHandler(MethodArgumentNotValidException.class) //this exception occurs when validation of data sent in requests fails
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex){

        Map<String, String> errors = new HashMap<>();

        //obtain all the fields that failed validation. For each error, the map is populated with a key and a value
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        //return a http response with status code of 400 (json with key-value pairs)
        return ResponseEntity.badRequest().body(errors);
    }
}
