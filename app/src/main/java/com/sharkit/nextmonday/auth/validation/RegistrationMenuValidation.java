package com.sharkit.nextmonday.auth.validation;

import android.content.Context;
import com.google.android.material.textfield.TextInputLayout;
import com.sharkit.nextmonday.auth.widget.AuthWidget;
import com.sharkit.nextmonday.configuration.validation.widget_validation.TextValidation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegistrationMenuValidation {

    private final AuthWidget.RegisterMenuWidget widget;
    private final Context context;

    public boolean validateField() {
        if (isNameValid(widget.getUserNameLayout())) {
            return Boolean.FALSE;
        }
        if (isNameValid(widget.getUserLastNameLayout())){
            return Boolean.FALSE;
        }
        if (isValidEmail(widget.getEmailLayout())) {
            return Boolean.FALSE;
        }
        if (isPasswordValid(widget.getPasswordLayout())) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private boolean isValidEmail(TextInputLayout inputLayout) {
        return new TextValidation(inputLayout, context)
                .isValidEmail()
                .build();
    }

    private boolean isNameValid(TextInputLayout inputLayout) {
        return new TextValidation(inputLayout, context)
                .notEmpty()
                .hasNoNumber()
                .hasNoSpace()
                .hasNoSymbols()
                .tooLongValue(15)
                .tooShortValue(1)
                .build();
    }

    private boolean isPasswordValid(TextInputLayout inputLayout) {
        return new TextValidation(inputLayout, context)
                .notEmpty()
                .hasNoSpace()
                .tooLongValue(50)
                .tooShortValue(4)
                .hasNotCyrillic()
                .build();
    }
}
