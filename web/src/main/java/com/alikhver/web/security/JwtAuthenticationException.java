package com.alikhver.web.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.AuthenticationException;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(String explanation) {
        super(explanation);
    }
}
