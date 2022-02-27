package com.alikhver.web.exeption.profile;

public class NoProfileFoundException extends RuntimeException {
    public NoProfileFoundException(String message) {
        super(message);
    }
}
