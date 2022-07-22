package com.sharkit.nextmonday.configuration.validation.field_validation;

import com.sharkit.nextmonday.configuration.validation.widget_validation.TextValidation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterMenuValidator {

    public static Boolean isValidEmail(String textFromField) {
        return new TextValidation(textFromField)
                .isValidEmail()
                .build();
    }

    public static Boolean isNameValid(String textFromField) {
        return new TextValidation(textFromField)
                .notEmpty()
                .hasNoNumber()
                .hasNoSpace()
                .hasNoSymbols()
                .tooLongValue(15)
                .tooSmallValue(1)
                .build();
    }

    public static Boolean isPasswordValid(String textFromField) {
        return new TextValidation(textFromField)
                .notEmpty()
                .hasNoSpace()
                .tooLongValue(50)
                .tooSmallValue(6)
                .hasNotCyrillic()
                .build();
    }

}
