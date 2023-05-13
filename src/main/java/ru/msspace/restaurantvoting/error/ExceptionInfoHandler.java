package ru.msspace.restaurantvoting.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionInfoHandler {
    private static final String BAD_REQUEST = "Bad request";
    private static final String NOT_FOUND = "Wrong data in request";
    private static final String DATA_CONFLICT = "DB data conflict";

    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse notFoundError(Exception ex, HttpServletRequest req) {
        return logAndGetErrorInfo(req, ex, HttpStatus.NOT_FOUND, NOT_FOUND);
    }

    @ExceptionHandler({IllegalRequestDataException.class, BindException.class, ValidationException.class})
    public ErrorResponse illegalRequestDataError(Exception ex, HttpServletRequest req) {
        return logAndGetErrorInfo(req, ex, HttpStatus.UNPROCESSABLE_ENTITY, BAD_REQUEST);
    }

    @ExceptionHandler({DataIntegrityViolationException.class, DataConflictException.class})
    public ErrorResponse dataConflictError(Exception ex, HttpServletRequest req) {
        return logAndGetErrorInfo(req, ex, HttpStatus.CONFLICT, DATA_CONFLICT);
    }

    private ErrorResponse logAndGetErrorInfo(HttpServletRequest req, Exception ex, HttpStatus status, String detail) {
        ErrorResponse resp = ErrorResponse.builder(ex, status, detail).build();
        log.error("Exception '{}',  at request '{}'. {}",
                detail,
                req.getRequestURI(),
                ex.getMessage());
        return resp;
    }
}