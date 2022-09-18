package com.sharkit.nextmonday.auth.validation;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;
import com.sharkit.nextmonday.auth.widget.AuthWidget;
import com.sharkit.nextmonday.configuration.validation.TextValidation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegistrationMenuValidation {

    private final AuthWidget.RegisterMenuWidget widget;
    private final Context context;

    public boolean validateField() {
        if (!this.isNameValid(this.widget.getUserNameLayout())) {
            return Boolean.FALSE;
        }
        if (!this.isNameValid(this.widget.getUserLastNameLayout())) {
            return Boolean.FALSE;
        }
        if (!this.isValidEmail(this.widget.getEmailLayout())) {
            return Boolean.FALSE;
        }
        if (!this.isPasswordValid(this.widget.getPasswordLayout())) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private boolean isValidEmail(final TextInputLayout inputLayout) {
        return new TextValidation(inputLayout, this.context)
                .isValidEmail()
                .build();
    }

    private boolean isNameValid(final TextInputLayout inputLayout) {
        return new TextValidation(inputLayout, this.context)
                .notEmpty()
                .hasNoNumber()
                .hasNoSpace()
                .hasNoSymbols()
                .tooLongValue(15)
                .tooShortValue(1)
                .build();
    }

    private boolean isPasswordValid(final TextInputLayout inputLayout) {
        return new TextValidation(inputLayout, this.context)
                .notEmpty()
                .hasNoSpace()
                .tooLongValue(50)
                .tooShortValue(4)
                .hasNotCyrillic()
                .build();
    }
}
