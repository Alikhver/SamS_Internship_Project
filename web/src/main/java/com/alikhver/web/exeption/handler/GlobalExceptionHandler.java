package com.alikhver.web.exeption.handler;

import com.alikhver.web.exeption.user.UserAlreadyExistsException;
import com.alikhver.web.exeption.user.UserDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<BadResponse> userAlreadyExistsExceptionHandler(UserAlreadyExistsException e) {
        return new ResponseEntity<>(BadResponse.builder()
                .info(e.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .build(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<BadResponse> userDoesNotExistExceptionHandler(UserDoesNotExistException e) {
        return new ResponseEntity<>(BadResponse.builder()
                .info(e.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build(), HttpStatus.NOT_FOUND);
    }
}
