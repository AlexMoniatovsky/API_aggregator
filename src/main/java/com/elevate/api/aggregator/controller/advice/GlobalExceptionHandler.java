package com.elevate.api.aggregator.controller.advice;

import com.elevate.api.aggregator.exception.HttpClientException;
import com.elevate.api.aggregator.exception.InternalException;
import com.elevate.api.aggregator.exception.Not2xxCodeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // 409
    @ExceptionHandler(Not2xxCodeException.class)
    public void handleNot2xxCodeException() {
        // return some Response entity here
    }

    @ResponseStatus(HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(HttpClientException.class)
    public void handleHttpClientException() {
        // return some Response entity here
        // maybe it make sense to propogate BAD_REQUEST or UNATHORIZED that ware return from target resources
    }

    @ResponseStatus(HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(InternalException.class)
    public void handleInternalException() {
        // return some Response entity here
    }

}
