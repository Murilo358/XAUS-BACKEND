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
    public ResponseEntity<String> handleNotFoundException(CustomException message) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message.getMessage());
    }

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<Object> handleOutOfStockException(OutOfStockException message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message.getMessage());
    }



}