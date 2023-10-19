package com.sumerge.authservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(UserNotFoundException e) {

        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(e.getMessage());
        error.setErrorClass(e.getClass().getName());
        error.setTimeStamp(new Date());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND) ;
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(NotValidMailException e) {

        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.FORBIDDEN.value());
        error.setMessage(e.getMessage());
        error.setErrorClass(e.getClass().getName());
        error.setTimeStamp(new Date());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception e) {

        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(e.getMessage());
        error.setErrorClass(e.getClass().getName());
        error.setTimeStamp(new Date());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST) ;
    }



}