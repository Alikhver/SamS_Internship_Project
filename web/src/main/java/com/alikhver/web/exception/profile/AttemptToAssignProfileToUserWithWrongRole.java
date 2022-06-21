package com.alikhver.web.exception.profile;

import com.alikhver.web.exception.CustomLocalizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AttemptToAssignProfileToUserWithWrongRole extends CustomLocalizedException {
    {
        status = HttpStatus.CONFLICT;
    }

    public AttemptToAssignProfileToUserWithWrongRole(Long id) {
        super("AttemptToAssignProfileToUserWithWrongRole.msg", id.toString());
    }
}
