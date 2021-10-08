package com.sharkit.nextmonday.configuration.validation;

public interface ValidationMethod {
    boolean isValidEmail(String email);
    boolean hasConnection();
}
