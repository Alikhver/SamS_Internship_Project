package com.alikhver.web.exception.organisation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoOrganisationFoundException extends RuntimeException {
    public NoOrganisationFoundException(String message) {
        super(message);
    }
}
