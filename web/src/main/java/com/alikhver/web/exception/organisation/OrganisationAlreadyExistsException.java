package com.alikhver.web.exception.organisation;

import com.alikhver.web.exception.CustomLocalizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class OrganisationAlreadyExistsException extends CustomLocalizedException {
    {
        status = HttpStatus.CONFLICT;
    }
    public OrganisationAlreadyExistsException() {
        super("OrganisationAlreadyExistsException.msg");
    }
}
