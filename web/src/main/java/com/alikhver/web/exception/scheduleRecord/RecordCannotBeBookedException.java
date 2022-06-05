package com.alikhver.web.exception.scheduleRecord;

import com.alikhver.web.exception.handler.Messages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;

@ResponseStatus(HttpStatus.CONFLICT)
public class RecordCannotBeBookedException extends RuntimeException {
    private Locale locale;
    private Long param;

    public RecordCannotBeBookedException(String messageKey, Locale locale, Long param) {
        super(messageKey);
        this.locale = locale;
        this.param = param;

    }

    @Override
    public String getLocalizedMessage() {
        return Messages.getMessageForLocale(getMessage(), locale, param);
    }
}
