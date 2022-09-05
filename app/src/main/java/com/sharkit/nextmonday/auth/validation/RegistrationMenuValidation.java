package com.sharkit.nextmonday.auth.validation;

import android.content.Context;

import com.google.android.material.textfield.TextInputEditText;
import com.sharkit.nextmonday.configuration.validation.widget_validation.TextValidation;
import com.sharkit.nextmonday.configuration.widget_finder.WidgetContainer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegistrationMenuValidation {

    private final WidgetContainer.RegisterMenuWidget widget;
    private final Context context;

    public boolean validateField() {
        if (isNameValid(widget.getUserName())) {
            return Boolean.FALSE;
        }
        if (isNameValid(widget.getUserLastName())){
            return Boolean.FALSE;
        }
        if (isValidEmail(widget.getEmail())) {
            return Boolean.FALSE;
        }
        if (isPasswordValid(widget.getPassword())) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private boolean isValidEmail(TextInputEditText emailWidget) {
        return new TextValidation(emailWidget, context)
                .isValidEmail()
                .build();
    }

    private boolean isNameValid(TextInputEditText widget) {
        return new TextValidation(widget, context)
                .notEmpty()
                .hasNoNumber()
                .hasNoSpace()
                .hasNoSymbols()
                .tooLongValue(15)
                .tooShortValue(1)
                .build();
    }

    private boolean isPasswordValid(TextInputEditText widget) {
        return new TextValidation(widget, context)
                .notEmpty()
                .hasNoSpace()
                .tooLongValue(50)
                .tooShortValue(4)
                .hasNotCyrillic()
                .build();
    }
}
