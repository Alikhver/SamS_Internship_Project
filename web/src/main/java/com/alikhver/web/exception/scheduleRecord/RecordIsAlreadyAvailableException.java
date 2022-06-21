package com.alikhver.web.exception.scheduleRecord;

import com.alikhver.web.exception.CustomLocalizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class RecordIsAlreadyAvailableException extends CustomLocalizedException {
    {
        status = HttpStatus.CONFLICT;
    }
    public RecordIsAlreadyAvailableException() {
        super("RecordIsAlreadyAvailableException.msg");
    }
}
