package com.alikhver.web.exception.organisation;

import com.alikhver.web.exception.CustomLocalizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class OrganisationIsAlreadyLaunchedException extends CustomLocalizedException {
    {
        status = HttpStatus.CONFLICT;
    }

    public OrganisationIsAlreadyLaunchedException(Long id) {
        super("OrganisationIsAlreadyLaunchedException.msg.id", id.toString());
    }
}
