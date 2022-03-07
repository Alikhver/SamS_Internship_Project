package com.alikhver.web.exeption.utility;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UtilityAlreadyExistsException extends RuntimeException{
    public UtilityAlreadyExistsException(String message) {
        super(message);
    }
}