package com.intern.project1.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ResponseStatus(HttpStatus.TEMPORARY_REDIRECT)
public class NotLoggedInException extends RuntimeException{


    String redirectUri;

    public NotLoggedInException(String message){
        super(message);
    }
    public NotLoggedInException(final String message, final String uri) {
        super(message);
        redirectUri = uri;
    }

    public String getRedirectUri() {
        return redirectUri;
    }
}
