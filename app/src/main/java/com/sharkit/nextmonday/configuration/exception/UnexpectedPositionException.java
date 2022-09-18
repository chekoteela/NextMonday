package com.sharkit.nextmonday.configuration.exception;

public class UnexpectedPositionException extends RuntimeException{

    private static final String MESSAGE = "Unexpected position : %s";

    public UnexpectedPositionException(final int value) {
        super(String.format(MESSAGE, value));
    }
}
