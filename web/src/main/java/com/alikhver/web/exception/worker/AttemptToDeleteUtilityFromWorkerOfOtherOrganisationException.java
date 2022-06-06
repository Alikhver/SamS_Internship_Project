package com.alikhver.web.exception.worker;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AttemptToDeleteUtilityFromWorkerOfOtherOrganisationException extends RuntimeException {
    public AttemptToDeleteUtilityFromWorkerOfOtherOrganisationException() {
        super("AttemptToDeleteUtilityFromWorkerOfOtherOrganisationException.msg");
    }
}
