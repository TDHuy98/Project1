package com.intern.project1.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class NotMatchInformationException extends RuntimeException{
    public NotMatchInformationException(String messages){
        super(messages);
    }
}
