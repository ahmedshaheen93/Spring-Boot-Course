package com.shaheen.demoapi.exception;

import org.springframework.http.HttpStatus;

public class BadRequestExpection extends BaseException {
    public BadRequestExpection(String message) {
        super(message);
    }

    @Override
    protected HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
