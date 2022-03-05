package com.alikhver.web.exeption.worker;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoWorkerFoundException extends RuntimeException{
    public NoWorkerFoundException(String message) {
        super(message);
    }
}
