package com.alikhver.web.exception.utility;

import com.alikhver.web.exception.CustomLocalizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UtilityAlreadyHasProvidedWorkerException extends CustomLocalizedException {
    public UtilityAlreadyHasProvidedWorkerException() {
        super("UtilityAlreadyHasProvidedWorkerException.msg");
    }
}
