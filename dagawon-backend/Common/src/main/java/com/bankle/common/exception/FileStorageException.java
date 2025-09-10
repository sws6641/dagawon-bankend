package com.bankle.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@Getter
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FileStorageException extends RuntimeException {
	public FileStorageException(String message) {
        super(message);
    }
 
    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}