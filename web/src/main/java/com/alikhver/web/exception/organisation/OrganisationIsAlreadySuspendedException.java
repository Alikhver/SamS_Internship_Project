package com.alikhver.web.exception.organisation;

import com.alikhver.web.exception.CustomLocalizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class OrganisationIsAlreadySuspendedException extends CustomLocalizedException {
    {
        status = HttpStatus.CONFLICT;
    }

    public OrganisationIsAlreadySuspendedException() {
        super("OrganisationIsAlreadySuspendedException.msg");
    }
}
