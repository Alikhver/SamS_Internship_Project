package com.alikhver.web.exception.scheduleRecord;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ScheduleRecordWithSuchWorkerAndTimeAlreadyExists extends RuntimeException {
    public ScheduleRecordWithSuchWorkerAndTimeAlreadyExists(String message) {
        super(message);
    }
}
