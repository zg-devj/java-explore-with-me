package ru.practicum.server.stats.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.practicum.server.stats.exceptions.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    // 400
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handlerBadRequestException(final BadRequestException e) {
        log.warn(e.getMessage());
        return new ApiError(HttpStatus.BAD_REQUEST.name(), "An incorrectly made request.",
                e.getMessage());
    }

    // 400 MissingServletRequestParameterException
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handlerMissingServletRequestParameterException(final MissingServletRequestParameterException e) {
        log.warn(e.getMessage());
        return new ApiError(HttpStatus.BAD_REQUEST.name(), "An incorrectly made request.",
                e.getMessage());
    }

    // 400 MethodArgumentTypeMismatch
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handlerMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException e) {
        log.warn(e.getMessage());
        return new ApiError(HttpStatus.BAD_REQUEST.name(), "An incorrectly made request.",
                e.getMessage());
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorExtended handlerMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.warn(e.getMessage());
        List<String> errors = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(
                fieldError -> errors.add(new ErrorMessageField(fieldError.getField(),
                        fieldError.getDefaultMessage()).getErrorMessage()));
        return new ApiErrorExtended(HttpStatus.BAD_REQUEST.name(), "An incorrectly made request.",
                "Invalid data.", errors);
    }

    // 404
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(final NotFoundException e) {
        log.warn(e.getMessage());
        return new ApiError(HttpStatus.NOT_FOUND.name(), "The required object was not found.", e.getMessage());
    }

    // 409
    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflictException(ConflictException e) {
        log.warn(e.getMessage());
        return new ApiError(HttpStatus.CONFLICT.name(), e.getReason(), e.getMessage());
    }

    // 500
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorExtended handlerException(final Exception e) {
        log.error("Error", e);
        StringWriter out = new StringWriter();
        e.printStackTrace(new PrintWriter(out));
        String stackTrace = out.toString();
        return new ApiErrorExtended(HttpStatus.INTERNAL_SERVER_ERROR.name(), "An unexpected error has occurred.",
                e.getMessage(), Collections.singletonList(stackTrace));
    }
}
