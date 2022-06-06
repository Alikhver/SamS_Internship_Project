package com.alikhver.web.exception.user;

import com.alikhver.web.exception.CustomLocalizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends CustomLocalizedException {

    public UserAlreadyExistsException(String login) {
        super("UserAlreadyExistsException.msg.login", login);
    }
}
