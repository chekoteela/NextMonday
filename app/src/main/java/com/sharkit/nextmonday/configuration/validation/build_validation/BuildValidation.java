package com.sharkit.nextmonday.configuration.validation.build_validation;

import android.content.Context;
import android.widget.EditText;

import com.sharkit.nextmonday.configuration.validation.impl.ValidationMethod;

import java.util.ArrayList;
import java.util.List;

public class BuildValidation implements ValidationMethod {

    public BuildValidation(Context context){
        validation = new Validation(context);
    }
    private final Validation validation;
    private final List<Boolean> isValid = new ArrayList<>();

    @Override
    public ValidationMethod tooSmallValue(int size) {
        isValid.add(validation.tooSmallValue(size));
        return this;
    }

    @Override
    public ValidationMethod tooSmallValue(int size, String toastMessage) {
        isValid.add(validation.tooSmallValue(size, toastMessage));
        return this;
    }

    @Override
    public ValidationMethod tooSmallValue(float size) {
        isValid.add(validation.tooSmallValue(size));
        return this;
    }

    @Override
    public ValidationMethod tooSmallValue(float size, String toastMessage) {
        isValid.add(validation.tooSmallValue(size,toastMessage));
        return this;
    }

    @Override
    public ValidationMethod tooBigValue(int size) {
        isValid.add(validation.tooBigValue(size));
        return this;
    }

    @Override
    public ValidationMethod tooBigValue(int size, String toastMessage) {
        isValid.add(validation.tooBigValue(size, toastMessage));
        return this;
    }

    @Override
    public ValidationMethod tooBigValue(float size) {
        isValid.add(validation.tooBigValue(size));
        return this;
    }

    @Override
    public ValidationMethod tooBigValue(float size, String toastMessage) {
        isValid.add(validation.tooBigValue(size, toastMessage));
        return this;
    }

    @Override
    public ValidationMethod isValidEmail() {
        isValid.add(validation.isValidEmail());
        return this;
    }

    @Override
    public ValidationMethod hasNoSymbols() {
        isValid.add(validation.hasNoSymbols());
        return this;
    }

    @Override
    public ValidationMethod notEmpty() {
        isValid.add(validation.notEmpty());
        return this;
    }

    @Override
    public ValidationMethod largerSizeFor(int size) {
        isValid.add(validation.largerSizeFor(size));
        return this;
    }

    @Override
    public ValidationMethod lessSizeFor(int size) {
        isValid.add(validation.lessSizeFor(size));
        return this;
    }

    @Override
    public ValidationMethod largerSizeFor(int size, String toastMessage) {
        isValid.add(validation.largerSizeFor(size,toastMessage));
        return this;
    }

    @Override
    public ValidationMethod lessSizeFor(int size, String toastMessage) {
        isValid.add(validation.lessSizeFor(size, toastMessage));
        return this;
    }

    @Override
    public ValidationMethod isValidEmail(String toastMessage) {
        isValid.add(validation.isValidEmail(toastMessage));
        return this;
    }

    @Override
    public ValidationMethod hasNoSymbols(String toastMessage) {
        isValid.add(validation.hasNoSymbols(toastMessage));
        return this;
    }

    @Override
    public ValidationMethod notEmpty(String toastMessage) {
        isValid.add(validation.notEmpty(toastMessage));
        return this;
    }

    @Override
    public ValidationMethod setWidget(EditText editText) {
        validation.setWidget(editText);
        return this;
    }

    @Override
    public ValidationMethod hasNoSpace() {
        isValid.add(validation.hasNoSpace());
        return this;
    }

    @Override
    public ValidationMethod hasNoSpace(String toastMessage) {
        isValid.add(validation.hasNoSpace(toastMessage));
        return this;
    }

    @Override
    public ValidationMethod hasNoNumber(String toastMessage) {
        isValid.add(validation.hasNoNumber(toastMessage));
        return this;
    }

    @Override
    public ValidationMethod hasNoNumber() {
        isValid.add(validation.hasNoNumber());
        return this;
    }

    @Override
    public ValidationMethod hasNotCyrillic() {
        isValid.add(validation.hasNotCyrillic());
        return this;
    }

    @Override
    public ValidationMethod hasNotCyrillic(String toastMessage) {
        isValid.add(validation.hasNotCyrillic(toastMessage));
        return this;
    }

    @Override
    public boolean build() {
        return !isValid.contains(false);
    }

}
