package com.intern.project1.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ResourceAlreadyExistedException.class,})
    public ResponseEntity<String> handleResourceAlreadyExistException(Exception e) {
        return ResponseEntity.status(409).body(e.getMessage());
    }

    @ExceptionHandler({ResourceNotFoundException.class,})
    public ResponseEntity<String> handleResourceNotFoundException(Exception e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler({WrongInformationException.class,})
    public ResponseEntity<String> handleWrongInformationException(Exception e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(value = {
            NotLoggedInException.class
    })
    protected ResponseEntity<Object> handleNotLoggedIn(
            final NotLoggedInException ex, final WebRequest request
    ) {
        final String bodyOfResponse = ex.getMessage();

        final HttpHeaders headers = new HttpHeaders();
        headers.add("Location", ex.getRedirectUri());
        return handleExceptionInternal(
                ex, bodyOfResponse,
                headers, HttpStatus.CREATED, request
        );
    }
    @ExceptionHandler(value = {
            NotCorrectTypeOfRequest.class
    })
    protected ResponseEntity<Object> handleNotCorrectTypeOfRequest(
            final NotCorrectTypeOfRequest ex, final WebRequest request
    ) {
        final String bodyOfResponse = ex.getMessage();

        final HttpHeaders headers = new HttpHeaders();
        headers.add("Location", ex.getRedirectUri());
        return handleExceptionInternal(
                ex, bodyOfResponse,
                headers, HttpStatus.TEMPORARY_REDIRECT, request
        );
    }

    @ExceptionHandler(value = {
            ProductNotFoundException.class
    })
    protected ResponseEntity<Object> handleProductNotFoundException(
            final ProductNotFoundException ex, final WebRequest request
    ) {
        final String bodyOfResponse = ex.getMessage();

        final HttpHeaders headers = new HttpHeaders();
        headers.add("Location", ex.getRedirectUri());
        return handleExceptionInternal(
                ex, bodyOfResponse,
                headers, HttpStatus.TEMPORARY_REDIRECT, request
        );
    }

    @ExceptionHandler({NotMatchInformationException.class})
    protected ResponseEntity<String> handleNotMatchInformationException(Exception e){
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String,String> errors=new HashMap<>();
        List<ObjectError> errorList=ex.getAllErrors();
        errorList.forEach((error)->{
            String fieldName=((FieldError) error).getField();
            String message= error.getDefaultMessage();
            errors.put(fieldName,message);
        });

        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }
@ExceptionHandler(NotEnoughResourceException.class)
    protected ResponseEntity<String>handleNotEnoughResourceException(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
}

}
