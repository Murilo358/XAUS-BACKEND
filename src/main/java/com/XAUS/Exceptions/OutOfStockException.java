package com.XAUS.Exceptions;

import org.springframework.http.HttpStatus;

public class OutOfStockException extends RuntimeException {

    public HttpStatus httpStatus;
    public OutOfStockException(String message,  HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
