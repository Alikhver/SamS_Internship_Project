package com.alikhver.web.exception.schedule_record;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoScheduleRecordFoundException extends RuntimeException{
    public NoScheduleRecordFoundException(String message) {
        super(message);
    }
}
