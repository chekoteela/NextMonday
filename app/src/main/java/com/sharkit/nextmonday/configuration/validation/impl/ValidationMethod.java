package com.sharkit.nextmonday.configuration.validation.impl;

import android.widget.EditText;

public interface ValidationMethod {
    ValidationMethod isValidEmail();
    ValidationMethod hasNoSymbols();
    ValidationMethod notEmpty();
    ValidationMethod largerSizeFor(int size);
    ValidationMethod lessSizeFor(int size);
    ValidationMethod largerSizeFor(int size, String toastMessage);
    ValidationMethod lessSizeFor(int size, String toastMessage);
    ValidationMethod isValidEmail(String toastMessage);
    ValidationMethod hasNoSymbols(String toastMessage);
    ValidationMethod notEmpty(String toastMessage);
    ValidationMethod setWidget(EditText editText);
    ValidationMethod hasNoSpace();
    ValidationMethod hasNoSpace(String toastMessage);
    ValidationMethod hasNoNumber(String toastMessage);
    ValidationMethod hasNoNumber();
    ValidationMethod hasNotCyrillic();
    ValidationMethod hasNotCyrillic(String toastMessage);


    boolean build();
}
