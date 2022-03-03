package com.alikhver.web.exeption.organisation;

public class OrganisationAlreadyExistsException extends RuntimeException {
    public OrganisationAlreadyExistsException(String message) {
        super(message);
    }
}
