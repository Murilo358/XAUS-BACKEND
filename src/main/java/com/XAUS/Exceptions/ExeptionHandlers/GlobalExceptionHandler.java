package com.XAUS.Exceptions.ExeptionHandlers;

import com.XAUS.Exceptions.OutOfStockException;
import com.XAUS.Exceptions.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleProductNotFound(CustomException message) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", message.getMessage());
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<Object> handleProductNotFound(OutOfStockException message) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", message.getMessage());
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }



}