package com.alikhver.web.exception.utility;

import com.alikhver.web.exception.CustomLocalizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoUtilityFoundException extends CustomLocalizedException {
    {
        status = HttpStatus.NOT_FOUND;
    }

    public NoUtilityFoundException(Long id) {
        super("NoUtilityFoundException.msg.id");
    }
}
