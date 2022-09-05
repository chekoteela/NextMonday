package com.sharkit.nextmonday.auth.validation;

import android.content.Context;

import com.sharkit.nextmonday.configuration.validation.widget_validation.TextValidation;
import com.sharkit.nextmonday.configuration.widget_finder.WidgetContainer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegistrationMenuValidation {

    private final WidgetContainer.RegisterMenuWidget widget;
    private final Context context;

    public boolean validateField() {
        if (isNameValid(widget.getUserName().getText().toString().trim())) {
            return Boolean.FALSE;
        }
        if (isNameValid(widget.getUserLastName().getText().toString().trim())) {
            return Boolean.FALSE;
        }
        if (isValidEmail(widget.getEmail().getText().toString().trim())) {
            return Boolean.FALSE;
        }
        if (isPasswordValid(widget.getPassword().getText().toString().trim())) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private boolean isValidEmail(String textFromField) {
        return new TextValidation(textFromField, context)
                .isValidEmail()
                .build();
    }

    private boolean isNameValid(String textFromField) {
        return new TextValidation(textFromField, context)
                .notEmpty()
                .hasNoNumber()
                .hasNoSpace()
                .hasNoSymbols()
                .tooLongValue(15)
                .tooShortValue(1)
                .build();
    }

    private boolean isPasswordValid(String textFromField) {
        return new TextValidation(textFromField, context)
                .notEmpty()
                .hasNoSpace()
                .tooLongValue(50)
                .tooShortValue(4)
                .hasNotCyrillic()
                .build();
    }
}
