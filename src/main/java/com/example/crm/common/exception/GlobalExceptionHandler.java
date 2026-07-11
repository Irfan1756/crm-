package com.example.crm.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(

            ResourceNotFoundException ex,

            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse();

        response.setTimestamp(LocalDateTime.now());

        response.setStatus(HttpStatus.NOT_FOUND.value());

        response.setError("Not Found");

        response.setMessage(ex.getMessage());

        response.setPath(request.getRequestURI());

        return new ResponseEntity<>(
                response,
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(

            MethodArgumentNotValidException ex,

            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse();

        response.setTimestamp(LocalDateTime.now());

        response.setStatus(HttpStatus.BAD_REQUEST.value());

        response.setError("Validation Failed");

        response.setMessage(
                ex.getBindingResult()
                        .getFieldError()
                        .getDefaultMessage());

        response.setPath(request.getRequestURI());

        return new ResponseEntity<>(
                response,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(

            Exception ex,

            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse();

        response.setTimestamp(LocalDateTime.now());

        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        response.setError("Internal Server Error");

        response.setMessage(ex.getMessage());

        response.setPath(request.getRequestURI());

        return new ResponseEntity<>(
                response,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}