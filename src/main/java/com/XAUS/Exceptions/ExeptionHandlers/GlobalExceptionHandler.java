package com.XAUS.Exceptions.ExeptionHandlers;

import com.XAUS.Exceptions.OutOfStockException;
import com.XAUS.Exceptions.XausException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(XausException.class)
    public ResponseEntity<Object> handleCustomException(XausException message) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", message.getMessage());
        return new ResponseEntity<>(responseBody, message.httpStatus);
    }

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<Object> handleOutOfStock(OutOfStockException message) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", message.getMessage());
        return new ResponseEntity<>(responseBody, message.httpStatus);
    }




}