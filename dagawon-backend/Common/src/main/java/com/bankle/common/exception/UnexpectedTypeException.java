package com.bankle.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnexpectedTypeException extends RuntimeException{
    public UnexpectedTypeException(String message) {
        super(message);
    }

    public UnexpectedTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
