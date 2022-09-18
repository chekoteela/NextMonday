package com.sharkit.nextmonday.configuration.exception;

public class GoogleApiException extends RuntimeException{

    public GoogleApiException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
