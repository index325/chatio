package com.index325.users.management.chat.io.controllers.exceptions;

import com.index325.users.management.chat.io.shared.exceptions.AppException;
import com.index325.users.management.chat.io.shared.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<StandardError> applicationError(AppException e, HttpServletRequest request) {
        StandardError err = new StandardError();

        err.setTimestamp(Instant.now());
        err.setStatus(HttpStatus.BAD_REQUEST.value());
        err.setError("Something wrong happened");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        err.setCode(e.getCode());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        StandardError err = new StandardError();

        err.setTimestamp(Instant.now());
        err.setStatus(HttpStatus.NOT_FOUND.value());
        err.setError("Resource not found");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        err.setCode(e.getCode());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

}
