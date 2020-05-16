package com.shaheen.demoapi.exception;

import org.springframework.http.HttpStatus;

public abstract class BaseException extends RuntimeException {
    
    protected BaseException(String message) {
        super(message);
    }

    protected abstract HttpStatus getHttpStatus();
}
