package com.alikhver.web.exception.worker;

import com.alikhver.web.exception.CustomLocalizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AttemptToAssignUtilityOfOtherOrganisationException extends CustomLocalizedException {
    public AttemptToAssignUtilityOfOtherOrganisationException() {
        super("AttemptToAssignUtilityOfOtherOrganisationException.msg");
    }
}
