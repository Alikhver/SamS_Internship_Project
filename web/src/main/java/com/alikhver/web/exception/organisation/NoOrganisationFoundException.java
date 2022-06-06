package com.alikhver.web.exception.organisation;

import com.alikhver.web.exception.CustomLocalizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoOrganisationFoundException extends CustomLocalizedException {
    public NoOrganisationFoundException(Long id) {
        super("NoOrganisationFoundException.msg.id", id.toString());
    }
    public NoOrganisationFoundException(String login) {
        super("NoOrganisationFoundException.msg.login", login);
    }
}
