package com.alikhver.web.exception.worker;

import com.alikhver.web.exception.CustomLocalizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoWorkerFoundException extends CustomLocalizedException {
    {
        status = HttpStatus.NOT_FOUND;
    }

    public NoWorkerFoundException(Long id) {
        super("NoWorkerFoundException.msg.id", id.toString());
    }
}
