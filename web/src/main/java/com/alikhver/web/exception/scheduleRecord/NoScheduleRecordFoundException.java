package com.alikhver.web.exception.scheduleRecord;

import com.alikhver.web.exception.handler.Messages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoScheduleRecordFoundException extends RuntimeException {

    public NoScheduleRecordFoundException(String messageKey, Locale locale, Long id) {

        super(Messages.getMessageForLocale(messageKey, locale, id));

    }
    public NoScheduleRecordFoundException(String messageKey, Locale locale) {

        super(Messages.getMessageForLocale(messageKey, locale));

    }
}
