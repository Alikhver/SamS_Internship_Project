package com.alikhver.web.exception.profile;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AttemptToAssignProfileToUserWithWrongRole extends RuntimeException {
    public AttemptToAssignProfileToUserWithWrongRole(String message) {
        super(message);
    }
}
