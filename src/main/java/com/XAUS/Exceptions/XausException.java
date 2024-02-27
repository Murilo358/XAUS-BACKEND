package com.XAUS.Exceptions;

import org.springframework.http.HttpStatus;

public class XausException extends RuntimeException {

    public HttpStatus httpStatus;

    public XausException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
