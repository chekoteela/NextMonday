package com.sharkit.nextmonday.configuration.validation.field_validation;

import com.sharkit.nextmonday.configuration.validation.widget_validation.TextValidation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthValidation {

    public static Boolean isValidAuthField(String textFromField) {
        return new TextValidation(textFromField)
                .notEmpty()
                .build();
    }
}
