package com.alikhver.web.exeption.organisation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class OrganisationIsAlreadySuspendedException extends RuntimeException {
    public OrganisationIsAlreadySuspendedException(String message) {
        super(message);
    }
}
