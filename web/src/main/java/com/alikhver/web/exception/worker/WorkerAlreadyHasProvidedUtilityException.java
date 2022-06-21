package com.alikhver.web.exception.worker;

import com.alikhver.web.exception.CustomLocalizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class WorkerAlreadyHasProvidedUtilityException extends CustomLocalizedException {
    {
        status = HttpStatus.CONFLICT;
    }

    public WorkerAlreadyHasProvidedUtilityException() {
        super("WorkerAlreadyHasProvidedUtilityException.msg");
    }
}
