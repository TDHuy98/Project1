package com.intern.project1.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotEnoughResourceException extends RuntimeException{
    public NotEnoughResourceException(String message){
        super(message);
    }
}
