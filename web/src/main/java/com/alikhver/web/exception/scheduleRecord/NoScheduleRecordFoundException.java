package com.alikhver.web.exception.scheduleRecord;

import com.alikhver.web.exception.CustomLocalizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoScheduleRecordFoundException extends CustomLocalizedException {
    {
        status = HttpStatus.NOT_FOUND;
    }

    public NoScheduleRecordFoundException(Long id) {
        super("NoScheduleRecordFoundException.msg.id", id.toString());

    }
    public NoScheduleRecordFoundException() {

        super("NoScheduleRecordFoundException.msg");

    }
}
