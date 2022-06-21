package com.alikhver.web.exception.user;

import com.alikhver.web.exception.CustomLocalizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoUserFoundException extends CustomLocalizedException {
    {
        status = HttpStatus.NOT_FOUND;
    }

    public NoUserFoundException(Long id) {
        super("NoUserFoundException.msg.id", id.toString());
    }

    public NoUserFoundException(String login) {
        super("NoUserFoundException.msg.login", login);
    }
}
