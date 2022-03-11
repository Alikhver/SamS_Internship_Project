package com.alikhver.web.exeption.worker;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AttemptToAssignUtilityOfOtherOrganisationException extends RuntimeException{
    public AttemptToAssignUtilityOfOtherOrganisationException(String message) {
        super(message);
    }
}
