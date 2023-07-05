package ru.practicum.mainservice.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.practicum.mainservice.exceptions.*;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    // 400
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handlerBadRequestException(final BadRequestException e) {
        log.warn(e.getMessage());
        return new ApiError(HttpStatus.BAD_REQUEST.name(), "An incorrectly made request.",
                e.getMessage(), Collections.emptyList());
    }

    // 400 MethodArgumentTypeMismatch
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handlerBadRequestException(final MethodArgumentTypeMismatchException e) {
        log.warn(e.getMessage());
        return new ApiError(HttpStatus.BAD_REQUEST.name(), "An incorrectly made request.",
                e.getMessage(), Collections.emptyList());
    }

    // 400
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handlerValidateException(final MethodArgumentNotValidException e) {
        log.warn(e.getMessage());
        StringBuilder errors = new StringBuilder();
        e.getBindingResult().getFieldErrors().forEach(
                fieldError -> errors.append(new ErrorMessageField(fieldError.getField(),
                        fieldError.getDefaultMessage()).getErrorMessage()));
        return new ApiError(HttpStatus.BAD_REQUEST.name(), "An incorrectly made request.",
                errors.toString(), Collections.emptyList());
    }

    // 404
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(final NotFoundException e) {
        log.warn(e.getMessage());
        return new ApiError(HttpStatus.NOT_FOUND.name(), "The required object was not found.", e.getMessage(),
                Collections.emptyList());
    }

    // 409
    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflictException(ConflictException e) {
        log.warn(e.getMessage());
        return new ApiError(HttpStatus.CONFLICT.name(), e.getReason(), e.getMessage(),
                Collections.emptyList());
    }

    // 500
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handlerException(final Exception e) {
        log.error("Error", e);
        StringWriter out = new StringWriter();
        e.printStackTrace(new PrintWriter(out));
        String stackTrace = out.toString();
        return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.name(), "An unexpected error has occurred.",
                e.getMessage(), Collections.singletonList(stackTrace));
    }
}
