package com.elevate.api.aggregator.exception;

public class Not2xxCodeException extends RuntimeException {

    public Not2xxCodeException(String message) {
        super(message);
    }
}
