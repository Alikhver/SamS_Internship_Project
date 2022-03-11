package com.alikhver.web.exeption.handler;

import com.alikhver.web.exeption.organisation.NoOrganisationFoundException;
import com.alikhver.web.exeption.organisation.OrganisationAlreadyExistsException;
import com.alikhver.web.exeption.organisation.OrganisationIsAlreadyLaunchedException;
import com.alikhver.web.exeption.organisation.OrganisationIsAlreadySuspendedException;
import com.alikhver.web.exeption.profile.NoProfileFoundException;
import com.alikhver.web.exeption.profile.UserIsAlreadyBoundedProfileException;
import com.alikhver.web.exeption.user.NoUserFoundException;
import com.alikhver.web.exeption.user.UserAlreadyExistsException;
import com.alikhver.web.exeption.utility.NoUtilityFoundException;
import com.alikhver.web.exeption.utility.UtilityAlreadyExistsException;
import com.alikhver.web.exeption.worker.NoWorkerFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(
            UserAlreadyExistsException e, HttpServletRequest request) {
        ErrorResponse response = buildErrorResponse(e, HttpStatus.CONFLICT, request);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoUserFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNoUserFoundException(
            NoUserFoundException e, HttpServletRequest request) {
        ErrorResponse response = buildErrorResponse(e, HttpStatus.NOT_FOUND, request);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoProfileFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNoProfileFoundException(
            NoProfileFoundException e, HttpServletRequest request) {
        ErrorResponse response = buildErrorResponse(e, HttpStatus.NOT_FOUND, request);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoOrganisationFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNoOrganisationFoundException(
            NoOrganisationFoundException e, HttpServletRequest request) {
        ErrorResponse response = buildErrorResponse(e, HttpStatus.NOT_FOUND, request);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = OrganisationAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleOrganisationAlreadyExistsException(
            OrganisationAlreadyExistsException e, HttpServletRequest request) {
        ErrorResponse response = buildErrorResponse(e, HttpStatus.CONFLICT, request);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(OrganisationIsAlreadySuspendedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleOrganisationIsAlreadySuspendedException(
            OrganisationIsAlreadySuspendedException e, HttpServletRequest request) {
        ErrorResponse response = buildErrorResponse(e, HttpStatus.CONFLICT, request);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(OrganisationIsAlreadyLaunchedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleOrganisationIsAlreadyLaunchedException(
            OrganisationIsAlreadyLaunchedException e, HttpServletRequest request) {
        ErrorResponse response = buildErrorResponse(e, HttpStatus.CONFLICT, request);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoWorkerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNoWorkerFoundException(
            NoWorkerFoundException e, HttpServletRequest request) {
        ErrorResponse response = buildErrorResponse(e, HttpStatus.CONFLICT, request);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UtilityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handle(UtilityAlreadyExistsException e, HttpServletRequest request) {
        ErrorResponse response = buildErrorResponse(e, HttpStatus.CONFLICT, request);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoUtilityFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handle(NoUtilityFoundException e, HttpServletRequest request) {
        ErrorResponse response = buildErrorResponse(e, HttpStatus.NOT_FOUND, request);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handle(IllegalArgumentException e, HttpServletRequest request) {
        ErrorResponse response = buildErrorResponse(e, HttpStatus.BAD_REQUEST, request);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserIsAlreadyBoundedProfileException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handle(UserIsAlreadyBoundedProfileException e, HttpServletRequest request) {
        ErrorResponse response = buildErrorResponse(e, HttpStatus.CONFLICT, request);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    private ErrorResponse buildErrorResponse(
            Exception e,
            HttpStatus httpStatus, HttpServletRequest request) {
        return ErrorResponse.builder()
                .message(e.getLocalizedMessage())
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .path(request.getServletPath())
                .build();
    }
}
