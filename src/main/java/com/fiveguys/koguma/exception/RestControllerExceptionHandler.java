package com.fiveguys.koguma.exception;

import com.fiveguys.koguma.data.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

@RestControllerAdvice(basePackages = {"com.fiveguys.koguma.controller", "com.fiveguys.koguma.service"})
public class RestControllerExceptionHandler {
    public RestControllerExceptionHandler() {
        System.out.println("########## RestControllerExceptionHandler Default Constructor Call...");
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleException(Exception ex) {
        return ResponseEntity.badRequest().body(ErrorDTO.builder().content(ex.getMessage()).build());
    }
}