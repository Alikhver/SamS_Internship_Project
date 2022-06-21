package com.alikhver.web.exception.profile;

import com.alikhver.web.exception.CustomLocalizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserIsAlreadyBoundedProfileException extends CustomLocalizedException {
    {
        status = HttpStatus.CONFLICT;
    }

    public UserIsAlreadyBoundedProfileException(Long id) {
        super("UserIsAlreadyBoundedProfileException.msg.id", id.toString());
    }
}
