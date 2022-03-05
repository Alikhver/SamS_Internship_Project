package com.alikhver.web.exeption.organisation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class OrganisationIsAlreadyLaunchedException extends RuntimeException{
    public OrganisationIsAlreadyLaunchedException(String message) {
        super(message);
    }
}
