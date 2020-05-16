package com.shaheen.demoapi.exception;

import org.springframework.http.HttpStatus;

public class EntityExistException extends BaseException {
    public EntityExistException(String message) {
        super(message);
    }

    @Override
    protected HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
