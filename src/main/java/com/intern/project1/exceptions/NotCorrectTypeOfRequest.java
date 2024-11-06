package com.intern.project1.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.TEMPORARY_REDIRECT)
public class NotCorrectTypeOfRequest extends RuntimeException{
    String redirectUri;

    public NotCorrectTypeOfRequest(final String message, final String uri) {
        super(message);
        redirectUri = uri;
    }

    public String getRedirectUri() {
        return redirectUri;
    }
}
