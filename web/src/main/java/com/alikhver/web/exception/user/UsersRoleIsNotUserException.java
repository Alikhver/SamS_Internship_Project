package com.alikhver.web.exception.user;

import com.alikhver.web.exception.CustomLocalizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UsersRoleIsNotUserException extends CustomLocalizedException {
    public UsersRoleIsNotUserException() {
        super("UsersRoleIsNotUserException.msg");
    }
}
