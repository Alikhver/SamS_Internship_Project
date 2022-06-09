package com.alikhver.web.exception.user;

import com.alikhver.web.exception.CustomLocalizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ProvidedUserIsNotRedactorOfOrganisation extends CustomLocalizedException {
    public ProvidedUserIsNotRedactorOfOrganisation() {
        super("ProvidedUserIsNotRedactorOfOrganisation.msg");
    }
}
