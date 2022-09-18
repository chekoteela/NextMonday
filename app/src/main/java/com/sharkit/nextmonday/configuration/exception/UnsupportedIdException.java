package com.sharkit.nextmonday.configuration.exception;

import android.util.Log;

public class UnsupportedIdException extends RuntimeException{

    private static final String MESSAGE = "Unsupported incoming id : %s";

    public UnsupportedIdException(final Integer id) {
        super(String.format(MESSAGE, id));
    }
}
