package com.intern.project1.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {
    String redirectUri;

    public ProductNotFoundException(String message, String uri) {
        super(message);
        redirectUri = uri;
    }

    public String getRedirectUri() {
        return redirectUri;
    }
}
