package ru.practicum.mainservice.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.mainservice.exceptions.ApiError;
import ru.practicum.mainservice.exceptions.BadRequestException;
import ru.practicum.mainservice.exceptions.NotFoundException;
import ru.practicum.mainservice.exceptions.ObjectAlreadyExistException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    // 400
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handlerBadRequestException(HttpStatus status, final BadRequestException e) {
        log.warn(e.getMessage());
        return new ApiError(status.name(), e.getResponse(), e.getMessage());
    }

//    // 400
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public List<ApiError> handlerValidateException(final MethodArgumentNotValidException e) {
//        log.warn(e.getMessage());
//        List<ErrorMessageField> errors = new ArrayList<>();
//        e.getBindingResult().getFieldErrors().forEach(
//                fieldError -> {
//                    errors.add(new ErrorMessageField(fieldError.getField(), fieldError.getDefaultMessage()));
//                });
//        return errors;
//    }

    // 404
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(HttpStatus status, final NotFoundException e) {
        log.warn(e.getMessage());
        return new ApiError(status.name(), e.getResponse(), e.getMessage());
    }

    // 409
    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleObjectAlreadyExistException(final ObjectAlreadyExistException e) {
        log.warn(e.getMessage());
        return new ApiError(HttpStatus.CONFLICT.name(), e.getResponse(), e.getMessage());
    }

//    // 500
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ApiError handlerException(HttpStatus status, final RuntimeException e) {
//        String msg = "Произошла непредвиденная ошибка.";
//        log.warn(msg);
//        return new ApiError(status.name(), msg, "Ошибка сервера");
//    }
}
