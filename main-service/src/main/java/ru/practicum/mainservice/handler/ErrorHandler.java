package ru.practicum.mainservice.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.mainservice.exceptions.BadRequestException;
import ru.practicum.mainservice.exceptions.ErrorMessage;
import ru.practicum.mainservice.exceptions.NotFoundException;
import ru.practicum.mainservice.exceptions.ObjectAlreadyExistException;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    // 400
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handlerBadRequestException(final BadRequestException e) {
        log.warn(e.getMessage());
        String status = HttpStatus.BAD_REQUEST.name();
        return new ErrorMessage(status, e.getResponse(), e.getMessage());
    }

    // 404
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleNotFoundException(final NotFoundException e) {
        log.warn(e.getMessage());
        String status = HttpStatus.NOT_FOUND.name();
        return new ErrorMessage(status, e.getResponse(), e.getMessage());
    }

    // 409
    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage handleObjectAlreadyExistException(final ObjectAlreadyExistException e) {
        log.warn(e.getMessage());
        String status = HttpStatus.CONFLICT.name();
        return new ErrorMessage(status, e.getResponse(), e.getMessage());
    }
}
