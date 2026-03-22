package com.nilsson.wigellApi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationError(MethodArgumentNotValidException e){
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(err ->
            errors.put(err.getField(), err.getDefaultMessage()));

        Map<String, Object> response = buildResponse(
                HttpStatus.BAD_REQUEST,
                "Invalid input data",
                e.getMessage());
        response.put("affected fields", errors);
    
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
}

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleMemberNotFound(MemberNotFoundException e){
        Map<String, Object> response = buildResponse(
                HttpStatus.NOT_FOUND,
                "Not found",
                e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AuthorizationDeniedException e){
        Map<String, Object> response = buildResponse(
                HttpStatus.FORBIDDEN,
                "Forbidden access",
                e.getMessage() + ": You do not have the right authorization to reach this data");

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFound(UsernameNotFoundException e){
        Map<String, Object> response = buildResponse(
                HttpStatus.NOT_FOUND,
                "Not found",
                e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException e){
        Map<String, Object> response = buildResponse(
                HttpStatus.BAD_REQUEST,
                "Bad request",
                e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception e){
        Map<String, Object> response = buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error, contact support",
                e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    private static Map<String, Object> buildResponse(HttpStatus internalServerError, String value, String message) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", internalServerError.value());
        response.put("error", value);
        response.put("message", message);
        return response;
    }
}
