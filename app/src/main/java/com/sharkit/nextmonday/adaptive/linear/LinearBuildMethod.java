package com.sharkit.nextmonday.adaptive.linear;

import android.widget.EditText;

public interface LinearBuildMethod {

    LinearBuildMethod setParam(float heightCoefficient, float widthCoefficient);
    LinearBuildMethod setParam(int heightCoefficient, int widthCoefficient);


    LinearBuildMethod setWidget(EditText editText);
    LinearBuild build();
}
