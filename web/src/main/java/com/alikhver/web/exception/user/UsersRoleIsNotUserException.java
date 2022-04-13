package com.alikhver.web.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UsersRoleIsNotUserException extends RuntimeException{
    public UsersRoleIsNotUserException(String message) {
        super(message);
    }
}
