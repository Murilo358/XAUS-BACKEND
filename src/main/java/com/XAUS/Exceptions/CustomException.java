package com.XAUS.Exceptions;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

    public HttpStatus httpStatus;

    public CustomException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
