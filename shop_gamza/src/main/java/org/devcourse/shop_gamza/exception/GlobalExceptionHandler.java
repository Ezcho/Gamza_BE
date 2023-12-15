package org.devcourse.shop_gamza.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.devcourse.shop_gamza.controller.api.ApiResponse;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler
    public ApiResponse illegalExHandler(IllegalArgumentException e) {
        log.error(e.getMessage());

        return ApiResponse.error(BAD_REQUEST, e.getMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler
    public ApiResponse methodArgumentNotValidExHandler(MethodArgumentNotValidException e) {
        log.error(e.getMessage());

        String errorMessages = e.getBindingResult().getAllErrors().stream()
                .map(c -> getErrorMessage(c))
                .toList()
                .toString();

        return ApiResponse.error(BAD_REQUEST, errorMessages);
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler
    public ApiResponse entityNotFoundExHandler(EntityNotFoundException e) {
        return ApiResponse.error(NOT_FOUND, e.getMessage());
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ApiResponse exceptionHandler(Exception e) {
        log.error(e.getMessage());
        e.printStackTrace();

        return ApiResponse.error(INTERNAL_SERVER_ERROR, e.getMessage());
    }

    private String getErrorMessage(ObjectError error) {
        String[] codes = error.getCodes();
        for (String code : codes) {
            try {
                return messageSource.getMessage(code, error.getArguments(), Locale.KOREA);
            } catch (NoSuchMessageException ignored) {
            }
        }
        return error.getDefaultMessage();
    }
}
