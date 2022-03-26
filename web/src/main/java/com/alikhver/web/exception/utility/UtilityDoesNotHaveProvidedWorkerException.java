package com.alikhver.web.exception.utility;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UtilityDoesNotHaveProvidedWorkerException extends RuntimeException{
    public UtilityDoesNotHaveProvidedWorkerException(String message) {
        super(message);
    }
}
