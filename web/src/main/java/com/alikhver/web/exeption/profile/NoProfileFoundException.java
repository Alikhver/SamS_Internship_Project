package com.alikhver.web.exeption.profile;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoProfileFoundException extends RuntimeException {
    public NoProfileFoundException(String message) {
        super(message);
    }
}
