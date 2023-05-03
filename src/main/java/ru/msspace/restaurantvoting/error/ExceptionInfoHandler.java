package ru.msspace.restaurantvoting.error;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionInfoHandler {
    private static final String BAD_REQUEST = "Bad request";
    private static final String NOT_FOUND = "Wrong data in request";
    private static final String DATA_CONFLICT = "DB data conflict";

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> notFoundError(NotFoundException exception, HttpServletRequest req) {
        return logAndGetErrorInfo(req, exception, HttpStatus.NOT_FOUND, NOT_FOUND);
    }

    @ExceptionHandler(IllegalRequestDataException.class)
    public ResponseEntity<String> illegalRequestDataError(IllegalRequestDataException exception, HttpServletRequest req) {
        return logAndGetErrorInfo(req, exception, HttpStatus.UNPROCESSABLE_ENTITY, BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> dataIntegrityViolationError(DataIntegrityViolationException exception, HttpServletRequest req) {
        return logAndGetErrorInfo(req, exception, HttpStatus.CONFLICT, DATA_CONFLICT);
    }

    private ResponseEntity<String> logAndGetErrorInfo(HttpServletRequest req, Exception e, HttpStatus httpStatus, String error) {
        log.error("{}. Exception '{}', at request {}. {}", error, httpStatus, req.getRequestURL(), e.getLocalizedMessage());
        return ResponseEntity.status(httpStatus).body(error);
    }
}