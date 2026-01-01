package com.casey.aimihired.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Handle Validation Errors (e.g., password too short)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage()));
        
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // 2. Handle Database Conflicts (e.g., email already exists)
    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDatabaseErrors(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Registration failed: This email or username is already taken.");
        
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    // 3. Catch-all for everything else (The 500 safety net)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralErrors(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Something went wrong on our end.");
        
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}