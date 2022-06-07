package com.alikhver.web.exception.scheduleRecord;

import com.alikhver.web.exception.CustomLocalizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongUtilityAndWorkerParamsException extends CustomLocalizedException {
    public WrongUtilityAndWorkerParamsException() {
        super("WrongUtilityAndWorkerParamsException.msg");
    }
}
