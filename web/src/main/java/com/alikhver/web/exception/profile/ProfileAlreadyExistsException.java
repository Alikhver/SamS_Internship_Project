package com.alikhver.web.exception.profile;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProfileAlreadyExistsException extends RuntimeException{
    public ProfileAlreadyExistsException(String message) {
        super(message);
    }
}
