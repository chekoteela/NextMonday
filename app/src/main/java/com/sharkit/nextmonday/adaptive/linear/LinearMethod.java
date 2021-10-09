package com.sharkit.nextmonday.adaptive.linear;

import android.widget.EditText;

public interface LinearMethod {
    void setParam(float heightCoefficient, float widthCoefficient);
    void setParam(int heightCoefficient, int widthCoefficient);


    EditText getWidget();
    void setWidget(EditText editText);
}
