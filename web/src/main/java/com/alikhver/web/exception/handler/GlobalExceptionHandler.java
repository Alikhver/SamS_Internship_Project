package com.alikhver.web.exception.handler;

import com.alikhver.web.exception.organisation.NoOrganisationFoundException;
import com.alikhver.web.exception.organisation.OrganisationAlreadyExistsException;
import com.alikhver.web.exception.organisation.OrganisationIsAlreadyLaunchedException;
import com.alikhver.web.exception.organisation.OrganisationIsAlreadySuspendedException;
import com.alikhver.web.exception.profile.NoProfileFoundException;
import com.alikhver.web.exception.profile.UserIsAlreadyBoundedProfileException;
import com.alikhver.web.exception.scheduleRecord.NoScheduleRecordFoundException;
import com.alikhver.web.exception.scheduleRecord.ScheduleRecordWithSuchWorkerAndTimeAlreadyExists;
import com.alikhver.web.exception.scheduleRecord.UtilityIsAlreadyAvailableException;
import com.alikhver.web.exception.user.NoUserFoundException;
import com.alikhver.web.exception.user.UserAlreadyExistsException;
import com.alikhver.web.exception.user.UsersRoleIsNotUserException;
import com.alikhver.web.exception.utility.NoUtilityFoundException;
import com.alikhver.web.exception.utility.UtilityAlreadyExistsException;
import com.alikhver.web.exception.utility.UtilityDoesNotHaveProvidedWorkerException;
import com.alikhver.web.exception.worker.AttemptToAssignUtilityOfOtherOrganisationException;
import com.alikhver.web.exception.worker.AttemptToDeleteUtilityFromWorkerOfOtherOrganisationException;
import com.alikhver.web.exception.worker.NoWorkerFoundException;
import com.alikhver.web.exception.worker.WorkerAlreadyHasProvidedUtilityException;
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

    @ExceptionHandler(AttemptToAssignUtilityOfOtherOrganisationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handle(AttemptToAssignUtilityOfOtherOrganisationException e, HttpServletRequest request) {
        ErrorResponse response = buildErrorResponse(e, HttpStatus.CONFLICT, request);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(WorkerAlreadyHasProvidedUtilityException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handle(WorkerAlreadyHasProvidedUtilityException e, HttpServletRequest request) {
        ErrorResponse response = buildErrorResponse(e, HttpStatus.CONFLICT, request);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UtilityDoesNotHaveProvidedWorkerException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handle(UtilityDoesNotHaveProvidedWorkerException e, HttpServletRequest request) {
        ErrorResponse response = buildErrorResponse(e, HttpStatus.CONFLICT, request);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AttemptToDeleteUtilityFromWorkerOfOtherOrganisationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handle(AttemptToDeleteUtilityFromWorkerOfOtherOrganisationException e, HttpServletRequest request) {
        ErrorResponse response = buildErrorResponse(e, HttpStatus.CONFLICT, request);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoScheduleRecordFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handle(NoScheduleRecordFoundException e, HttpServletRequest request) {
        ErrorResponse response = buildErrorResponse(e, HttpStatus.NOT_FOUND, request);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsersRoleIsNotUserException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handle(UsersRoleIsNotUserException e, HttpServletRequest request) {
        ErrorResponse response = buildErrorResponse(e, HttpStatus.CONFLICT, request);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UtilityIsAlreadyAvailableException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handle(UtilityIsAlreadyAvailableException e, HttpServletRequest request) {
        ErrorResponse response = buildErrorResponse(e, HttpStatus.CONFLICT, request);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ScheduleRecordWithSuchWorkerAndTimeAlreadyExists.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handle(ScheduleRecordWithSuchWorkerAndTimeAlreadyExists e, HttpServletRequest request) {
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
