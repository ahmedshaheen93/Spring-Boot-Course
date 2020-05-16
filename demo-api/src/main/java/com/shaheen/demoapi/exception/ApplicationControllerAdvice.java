package com.shaheen.demoapi.exception;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.beans.PropertyEditorSupport;

@ControllerAdvice
public class ApplicationControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorDetails> handleApiException(BaseException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, ex.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGeneralApiException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ValidationError validationError = new ValidationError();
        validationError.setUri(request.getDescription(false));

        ex.getBindingResult()
                .getFieldErrors()
                .stream().forEach(fieldError -> setErrorMessage(validationError, fieldError, request));
        return new ResponseEntity<>(validationError, HttpStatus.BAD_REQUEST);
    }

    private void setErrorMessage(ValidationError validationError, FieldError fieldError, WebRequest request) {
        String errorMessage = fieldError.getDefaultMessage();
        validationError.addError(fieldError.getField(), errorMessage);
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
        dataBinder.registerCustomEditor(Number.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String str) {
                boolean check = str.matches("\\d+\\.?(\\d+)?");
                if (str.equals("") || !check)
                    setValue(0);
                else
                    setValue(Double.parseDouble(str));
            }
        });
    }
}
