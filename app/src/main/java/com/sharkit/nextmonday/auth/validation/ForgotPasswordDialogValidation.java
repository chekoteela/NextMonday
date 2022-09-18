package com.sharkit.nextmonday.auth.validation;

import android.content.Context;

import com.sharkit.nextmonday.auth.widget.AuthWidget;
import com.sharkit.nextmonday.configuration.validation.TextValidation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ForgotPasswordDialogValidation {

    private final Context context;
    private final AuthWidget.Dialog.ResetPasswordWidget widget;

    public boolean isValidEmail() {
        return new TextValidation(this.widget.getEmailLayout(), this.context)
                .notEmpty()
                .isValidEmail()
                .build();
    }
}
