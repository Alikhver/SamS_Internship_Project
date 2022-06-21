package com.alikhver.web.exception;

import com.alikhver.web.exception.handler.Messages;
import org.springframework.http.HttpStatus;

import java.util.Locale;

public abstract class CustomLocalizedException extends RuntimeException {
    public HttpStatus status;
    private String param;

    public CustomLocalizedException(String message) {
        super(message);
    }

    public CustomLocalizedException(String message, String param) {
        super(message);
        this.param = param;
    }

    public String getLocalizedMessage(Locale locale) {
        if (param == null) {
            return Messages.getMessageForLocale(getMessage(), locale);
        } else {
            return Messages.getMessageForLocale(getMessage(), locale, param);
        }
    }
}
