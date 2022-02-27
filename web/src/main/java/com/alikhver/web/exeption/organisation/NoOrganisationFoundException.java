package com.alikhver.web.exeption.organisation;

public class NoOrganisationFoundException extends RuntimeException {
    public NoOrganisationFoundException(String message) {
        super(message);
    }
}
