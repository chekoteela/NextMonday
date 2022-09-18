package com.sharkit.nextmonday.configuration.exception;

public class UnsupportedValueException extends RuntimeException{

    private static final String MESSAGE = "Unsupported value : %s";

    public UnsupportedValueException(final int value) {
        super(String.format(MESSAGE, value));
    }

    public UnsupportedValueException(final String value) {
        super(String.format(MESSAGE, value));
    }
}
