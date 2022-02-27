package com.alikhver.web.exeption.user;

public class NoUserFoundException extends Exception {
    public NoUserFoundException(String message) {
        super(message);
    }
}
