package com.bankle.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnAuthException extends RuntimeException {

    public UnAuthException(String message) {
        super(message);
    }

    public UnAuthException(String message, Throwable cause) {
        super(message, cause);
    }

}