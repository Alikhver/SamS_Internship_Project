package com.alikhver.web.exeption.utility;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoUtilityFoundException extends RuntimeException{
    public NoUtilityFoundException(String message) {
        super(message);
    }
}
