package com.shaheen.demoapi.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ValidationError {

    Map<String, String> errors = new HashMap<>(0);
    private String uri;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy hh:mm:ss")
    private Date timestamp;

    public ValidationError() {
        this.timestamp = new Date();
    }

    public Map<String, String> getFieldErrors() {
        return errors;
    }

    public void addError(String fieldName, String error) {
        errors.put(fieldName, error);
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
