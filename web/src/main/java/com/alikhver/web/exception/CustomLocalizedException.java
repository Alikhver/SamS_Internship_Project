package com.alikhver.web.exception;

import com.alikhver.web.exception.handler.Messages;

import java.util.Locale;

public class CustomLocalizedException extends RuntimeException {
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
