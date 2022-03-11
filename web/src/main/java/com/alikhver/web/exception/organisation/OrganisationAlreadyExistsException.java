package com.alikhver.web.exception.organisation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class OrganisationAlreadyExistsException extends RuntimeException {
    public OrganisationAlreadyExistsException(String message) {
        super(message);
    }
}
