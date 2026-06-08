package com.example.Course.Exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

//    @ExceptionHandler(RoleNotFoundException.class)
//    public ResponseEntity<String> handleRoleNotFoundException(RoleNotFoundException ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
//    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
//
//    @ExceptionHandler(PermissionNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handlePermissionNotFound(PermissionNotFoundException ex) {
//        ErrorResponse response = ErrorResponse.builder().status(HttpStatus.NOT_FOUND.value()).error(ex.getMessage())
//                .build();
//        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(InvalidRequestException.class)
//    public ResponseEntity<?> handleInvalidRequestException(InvalidRequestException ex) {
//        ErrorResponse response = ErrorResponse.builder().status(HttpStatus.NOT_FOUND.value()).error(ex.getMessage())
//                .build();
//        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(InvalidUserTypeException.class)
//    public ResponseEntity<ApiResponse<Object>> handleInvalidUserTypeException(InvalidUserTypeException ex) {
//        ApiResponse<Object> response = ApiResponse.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(DuplicateUserException.class)
//    public ResponseEntity<ApiResponse<Object>> handleDuplicateUserException(DuplicateUserException ex) {
//        ApiResponse<Object> response = ApiResponse.error(HttpStatus.CONFLICT.value(), ex.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
//    }
//
//    @ExceptionHandler(KeycloakUserCreationException.class)
//    public ResponseEntity<ApiResponse<Object>> handleKeycloakUserCreationException(KeycloakUserCreationException ex) {
//        ApiResponse<Object> response = ApiResponse.error(HttpStatus.BAD_GATEWAY.value(), ex.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
//    }
//
//    @ExceptionHandler(KeycloakOperationException.class)
//    public ResponseEntity<ApiResponse<Object>> handleKeycloakOperationException(KeycloakOperationException ex) {
//        ApiResponse<Object> response = ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex,
//                                                                        HttpServletRequest request) {
//        ErrorResponse error = ErrorResponse.builder().status(HttpStatus.BAD_REQUEST.value())
//                .message("Bad Request".concat(ex.getMessage())).path(request.getRequestURI()).build();
//        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(MissingLanguageHeaderException.class)
//    public ResponseEntity<ErrorResponse> handleMissingLanguageHeader(MissingLanguageHeaderException ex,
//                                                                     HttpServletRequest request) {
//
//        ErrorResponse error = ErrorResponse.builder().status(HttpStatus.BAD_REQUEST.value())
//                .message("Bad Request".concat(ex.getMessage())).path(request.getRequestURI()).build();
//
//        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(MissingRequestHeaderException.class)
//    public ResponseEntity<ErrorResponse> handleMissingRequestHeader(MissingRequestHeaderException ex,
//                                                                    HttpServletRequest request) {
//        ErrorResponse error = ErrorResponse.builder().status(HttpStatus.BAD_REQUEST.value()).error("Bad Request")
//                .message("Required request header '" + ex.getHeaderName() + "' is not present")
//                .path(request.getRequestURI()).build();
//        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//    }

}
