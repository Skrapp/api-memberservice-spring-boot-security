package com.nilsson.wigellApi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationError(MethodArgumentNotValidException e){
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(err ->
            errors.put(err.getField(), err.getDefaultMessage()));

    Map<String, Object> response = new LinkedHashMap<>();
    response.put("timestamp", LocalDateTime.now().toString());
    response.put("status", HttpStatus.BAD_REQUEST.value());
    response.put("error", "");
    response.put("message", e.getMessage());
    response.put("affected fields", errors);
    
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
}

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<?> handleMemberNotFound(MemberNotFoundException e){
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", "Not found");
        response.put("message", e.getMessage());
    
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(AccessDeniedException e){
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", HttpStatus.FORBIDDEN.value());
        response.put("error", "Forbidden access");
        response.put("message", e.getMessage());
        
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UsernameNotFoundException e){
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", "Not found");
        response.put("message", e.getMessage());
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e){
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Bad request");
        response.put("message", e.getMessage());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(Exception e){
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", "Internal server error, contact support");
        response.put("message", e.getMessage());
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
