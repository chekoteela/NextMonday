package com.sharkit.nextmonday.configuration.validation.impl;

import android.view.View;
import android.widget.EditText;

public interface BuildMethod {
    boolean tooSmallValue(float size);
    boolean tooSmallValue(float size, String toastMessage);
    boolean tooSmallValue(int size);
    boolean tooSmallValue(int size, String toastMessage);
    boolean tooBigValue(int size);
    boolean tooBigValue(int size, String toastMessage);
    boolean tooBigValue(float size);
    boolean tooBigValue(float size, String toastMessage);
    boolean isValidEmail();
    boolean hasNoSymbols();
    boolean notEmpty();
    boolean largerSizeFor(int size);
    boolean lessSizeFor(int size);
    boolean largerSizeFor(int size, String toastMessage);
    boolean lessSizeFor(int size, String toastMessage);
    boolean isValidEmail(String toastMessage);
    boolean hasNoSymbols(String toastMessage);
    boolean notEmpty(String toastMessage);
    boolean hasNoSpace();
    boolean hasNoSpace(String toastMessage);
    boolean hasNoNumber(String toastMessage);
    boolean hasNoNumber();
    boolean hasNotCyrillic();
    boolean hasNotCyrillic(String toastMessage);


    View getWidget();
    void setWidget(EditText editText);

}
