package com.XAUS.Exceptions.ExeptionHandlers;

import com.XAUS.Exceptions.OutOfStockException;
import com.XAUS.Exceptions.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException message) {
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