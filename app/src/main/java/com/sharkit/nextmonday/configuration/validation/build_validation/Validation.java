package com.sharkit.nextmonday.configuration.validation.build_validation;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.sharkit.nextmonday.configuration.validation.impl.BuildMethod;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation implements BuildMethod {
    private final Context context;
    private EditText editText;

    public Validation(Context context) {
        this.context = context;
    }

    @Override
    public boolean tooSmallValue(float size) {
        return Float.parseFloat(getWidget().getText().toString()) < size;
    }

    @Override
    public boolean tooSmallValue(float size, String toastMessage) {
        if (Float.parseFloat(getWidget().getText().toString()) < size) {
            setToastMessage(toastMessage);
            return false;
        }else {
            return true;
        }    }

    @Override
    public boolean tooSmallValue(int size) {
        return Integer.parseInt(getWidget().getText().toString()) < size;
    }

    @Override
    public boolean tooSmallValue(int size, String toastMessage) {
        if (Integer.parseInt(getWidget().getText().toString()) < size) {
            setToastMessage(toastMessage);
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean tooBigValue(int size) {
        return Integer.parseInt(getWidget().getText().toString()) > size;
    }

    @Override
    public boolean tooBigValue(int size, String toastMessage) {
        if (Integer.parseInt(getWidget().getText().toString()) > size) {
            setToastMessage(toastMessage);
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean tooBigValue(float size) {
        return Float.parseFloat(getWidget().getText().toString()) > size;
    }

    @Override
    public boolean tooBigValue(float size, String toastMessage) {
        if (Float.parseFloat(getWidget().getText().toString()) > size) {
            setToastMessage(toastMessage);
            return false;
        }else {
            return true;
        }      }

    @Override
    public boolean isValidEmail() {
        return !TextUtils.isEmpty(getWidget().getText().toString()) &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(getWidget().getText().toString()).matches();
    }

    @Override
    public boolean hasNoSymbols() {
        Pattern sign = Pattern.compile("[!@#$:%&*()_+=|<>?{}\\[\\]~×÷/€£¥₴^\";,°•○●□■♤♡◇♧☆▪¤《》¡¿.`]");
        Matcher hasSign = sign.matcher(getWidget().getText().toString());
        return !hasSign.find();
    }

    @Override
    public boolean notEmpty() {
        return !TextUtils.isEmpty(getWidget().getText());
    }

    @Override
    public boolean largerSizeFor(int size) {
        return getWidget().getText().length() > size;
    }

    @Override
    public boolean lessSizeFor(int size) {
        return getWidget().getText().length() < size;

    }

    @Override
    public boolean largerSizeFor(int size, String toastMessage) {
        if (getWidget().getText().length() > size){
            return true;
        }else {
            setToastMessage(toastMessage);
            return false;
        }
    }

    @Override
    public boolean lessSizeFor(int size, String toastMessage) {
        if (getWidget().getText().length() < size){
            return true;
        }else {
            setToastMessage(toastMessage);
            return false;
        }
    }

    @Override
    public boolean isValidEmail(String toastMessage) {
        if (!isValidEmail()){
            setToastMessage(toastMessage);
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean hasNoSymbols(String toastMessage) {
        Pattern sign = Pattern.compile("[!@#$:%&*()_+=|<>?{}\\[\\]~×÷/€£¥₴^\";,°•○●□■♤♡◇♧☆▪¤《》¡¿.`]");
        Matcher hasSign = sign.matcher(getWidget().getText().toString());
        if (hasSign.find()){
            setToastMessage(toastMessage);
            return false;
        }else {
            return true;
        }
    }
    @Override
    public boolean notEmpty(String toastMessage) {
        if (TextUtils.isEmpty(getWidget().getText())) {
            setToastMessage(toastMessage);
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean hasNoSpace() {
        Pattern space = Pattern.compile(" ");
        Matcher hasSpace = space.matcher(getWidget().getText().toString().trim());
        return !hasSpace.find();
    }

    @Override
    public boolean hasNoSpace(String toastMessage) {
        if (!hasNoSpace()){
            setToastMessage(toastMessage);
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean hasNoNumber(String toastMessage) {
        if (!hasNoNumber()){
            setToastMessage(toastMessage);
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean hasNoNumber() {
        Pattern num = Pattern.compile("[0-9]");
        Matcher hasNum = num.matcher(getWidget().getText().toString());
        return !hasNum.find();
    }

    @Override
    public boolean hasNotCyrillic() {
        Pattern cyrillic = Pattern.compile("[а-яА-Я]");
        Matcher hasCyrillic = cyrillic.matcher(getWidget().getText().toString());
        return !hasCyrillic.find();
    }

    @Override
    public boolean hasNotCyrillic(String toastMessage) {
        if (!hasNotCyrillic()){
            setToastMessage(toastMessage);
            return false;
        }else {
            return true;
        }
    }

    @Override
    public EditText getWidget() {
        return editText;
    }

    @Override
    public void setWidget(EditText editText) {
        this.editText = editText;
    }

    private void setToastMessage(String toastMessage){
        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
    }

}
