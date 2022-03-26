package com.alikhver.web.exception.utility;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UtilityAlreadyHasProvidedWorkerException extends RuntimeException {
    public UtilityAlreadyHasProvidedWorkerException(String message) {
        super(message);
    }
}
