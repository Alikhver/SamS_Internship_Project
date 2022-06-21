package com.alikhver.web.exception.profile;

import com.alikhver.web.exception.CustomLocalizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoProfileFoundException extends CustomLocalizedException {
    {
        status = HttpStatus.NOT_FOUND;
    }

    public NoProfileFoundException(Long id) {
        super("NoProfileFoundException.msg.id", id.toString());
    }
}
