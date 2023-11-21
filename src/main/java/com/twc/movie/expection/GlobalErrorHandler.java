package com.twc.movie.expection;

import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.twc.movie.model.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<String> handleRequestBodyError(WebExchangeBindException ex){
        log.error("Exception caught in handleRequestBodyError :  {} " ,ex.getMessage(),  ex);
        var error = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .sorted()
                .collect(Collectors.joining(","));
        log.error("errorList : {}", error);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleAllExceptions(Exception ex) {
        log.error(ex.getMessage(), ex.getLocalizedMessage());

        return new ErrorResponse(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage(), Collections.emptyList());
    }

    @ExceptionHandler({TenantDataSourceNotFoundException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse unauthorizedExceptionHandler(Exception ex) {
        log.error(ex.getMessage(), ex.getLocalizedMessage());
        return new ErrorResponse(String.valueOf(HttpStatus.UNAUTHORIZED.value()),HttpStatus.UNAUTHORIZED.getReasonPhrase(), ex.getMessage(), Collections.emptyList());
    }

}
