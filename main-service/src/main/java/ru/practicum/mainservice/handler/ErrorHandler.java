package ru.practicum.mainservice.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.mainservice.exceptions.*;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    // 400
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handlerBadRequestException(HttpServletResponse response, final BadRequestException e) {
        log.warn(e.getMessage());
        return new ApiError(HttpStatus.BAD_REQUEST.name(), "Неправильно сделанный запрос.",
                e.getMessage(), Collections.emptyList());
    }

    // 400
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handlerValidateException(HttpServletResponse response, final MethodArgumentNotValidException e) {
        log.warn(e.getMessage());
        StringBuilder errors = new StringBuilder();
        e.getBindingResult().getFieldErrors().forEach(
                fieldError -> {
                    errors.append(new ErrorMessageField(fieldError.getField(),
                            fieldError.getDefaultMessage()).getErrorMessage());
                });
        return new ApiError(HttpStatus.BAD_REQUEST.name(), "Неправильно сделанный запрос.",
                errors.toString(), Collections.emptyList());
    }

//    // 400
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ApiError handlerValidateException(HttpServletResponse response, final MethodArgumentNotValidException e) {
//        log.warn(e.getMessage());
//        List<String> errors = new ArrayList<>();
//        e.getBindingResult().getFieldErrors().forEach(
//                fieldError -> {
//                    errors.add(new ErrorMessageField(fieldError.getField(), fieldError.getDefaultMessage()).getErrorMessage());
//                });
//        return new ApiError(HttpStatus.valueOf(response.getStatus()).name(),
//                "Требуемый объект не был найден.", e.getMessage(), errors);
//    }

    // 404
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(HttpServletResponse response, final NotFoundException e) {
        log.warn(e.getMessage());
        return new ApiError(HttpStatus.NOT_FOUND.name(), "Требуемый объект не был найден.", e.getMessage(),
                Collections.emptyList());
    }

    // 409
    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleObjectAlreadyExistException(HttpServletResponse response, ObjectAlreadyExistException e) {
        log.warn(e.getMessage());
        return new ApiError(HttpStatus.CONFLICT.name(), "Ограничение целостности было нарушено.", e.getMessage(),
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
        return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.name(), "Произошла непредвиденная ошибка.",
                e.getMessage(), Collections.singletonList(stackTrace));
    }
}
